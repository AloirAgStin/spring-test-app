package com.spring.test.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Slf4j
@Validated
@RestController
@RequestMapping("/public-api")
public class PublicController {

    @GetMapping(path = "/test")
    String callPublicApi(@RequestParam @NotEmpty String text) {
        MDC.getMap().entrySet().forEach(stringObjectEntry -> {
            log.info("key {}, value {}", stringObjectEntry.getKey(), stringObjectEntry.getValue());
        });
        return text;
    }

}
