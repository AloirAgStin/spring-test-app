package com.spring.test.app_support.controller;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/app-support")
@RestController
public class ProcessIdController {

    private static final Long RANDOM_VALUE = RandomUtils.nextLong();

    @GetMapping
    public Long getProcessId() {
        return RANDOM_VALUE;
    }

}
