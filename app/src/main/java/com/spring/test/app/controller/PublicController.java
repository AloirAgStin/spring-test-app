package com.spring.test.app.controller;

import com.spring.test.app.client.AppSupportClient;
import com.spring.test.app.client.TestClient;
import com.spring.test.app.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Locale;


@Slf4j
@Validated
@RestController
@RequestMapping("/public-api")
@RequiredArgsConstructor
public class PublicController {

    private final TestClient testClient;

    private final AppSupportClient appSupportClient;

    private final MessageSource messageSource;

    private final ApplicationProperties applicationProperties;

    @GetMapping(path = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public String greeting(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale) {
        return messageSource.getMessage("greeting", null, locale);
    }

    @GetMapping(path = "/editable-property")
    public String getEditableProperty() {
        return applicationProperties.getEditableValue();
    }

    @GetMapping(path = "/echo-test")
    public String callPublicApi(@RequestParam @NotEmpty String text) {
        return text;
    }

    @GetMapping(path = "/test-feign")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void callFeignClient() {
        testClient.testFeign();
    }

    @GetMapping(path = "/pid")
    public Long pid() {
        return appSupportClient.getPid();
    }


}
