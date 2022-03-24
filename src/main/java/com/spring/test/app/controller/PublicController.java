package com.spring.test.app.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("/public-api")
public class PublicController {

    @GetMapping(path = "/test")
    String callPublicApi(@RequestParam @NotEmpty String text) {
        return text;
    }

}
