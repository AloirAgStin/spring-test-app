package com.spring.test.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api")
public class PublicController {

    @GetMapping(path = "/test")
    String callPublicApi(){
        return "callPublicApi";
    }

}
