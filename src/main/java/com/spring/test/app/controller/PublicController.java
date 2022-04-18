package com.spring.test.app.controller;

import com.spring.test.app.client.TestFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Slf4j
@Validated
@RestController
@RequestMapping("/public-api")
@RequiredArgsConstructor
public class PublicController {

    private final TestFeign testFeign;

    @GetMapping(path = "/test")
    public String callPublicApi(@RequestParam @NotEmpty String text) {
        return text;
    }

    @GetMapping(path = "/test-feign")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void callFeignClient() {
        testFeign.testFeign();
    }

}
