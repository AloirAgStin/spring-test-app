package com.spring.test.app.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/public-api")
public class PublicController {

    public static final String AUDIO_PATH = "/static/audio/";

    public static final int BYTE_RANGE = 128; // increase the byterange from here

    @GetMapping(path = "/test")
    String callPublicApi(@RequestParam @NotEmpty String text) {
        return text;
    }

    @GetMapping("/audio/{fileName}")
    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Range_requests
    public Mono<ResponseEntity<byte[]>> streamAudio(@RequestHeader(value = HttpHeaders.RANGE, required = false) String httpRangeList,
                                                    @PathVariable("fileName") String fileName) {
        return Mono.just(getContent(AUDIO_PATH, fileName, httpRangeList, "audio"));
    }

    private ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            fileSize = Optional.ofNullable(fileName)
                    .map(file -> Paths.get(getFilePath(location), file))
                    .map(this::sizeFromFile)
                    .orElse(0L);
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, contentTypePrefix + "/" + fileType)
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(readByteRange(location, fileName, rangeStart, fileSize - 1));
            }
            var objectRange= HttpRange.parseRanges(range)
                    .get(0); //todo multiranges
            rangeStart = objectRange.getRangeStart(fileSize);
            rangeEnd = objectRange.getRangeEnd(fileSize);
            data = readByteRange(location, fileName, rangeStart, rangeEnd);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, contentTypePrefix + "/" + fileType)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_LENGTH, contentLength)
                .header(HttpHeaders.CONTENT_RANGE, "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
    }

    public byte[] readByteRange(String location, String filename, long start, long end) throws IOException {
        var path = Paths.get(getFilePath(location), filename);
        try (InputStream inputStream = (Files.newInputStream(path));
             var bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }

    private String getFilePath(String location) {
        URL url = this.getClass().getResource(location);
        return new File(url.getFile()).getAbsolutePath();
    }

    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }


}
