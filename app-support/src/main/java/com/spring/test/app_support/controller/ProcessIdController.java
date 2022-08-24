package com.spring.test.app_support.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/app-support")
@RestController
public class ProcessIdController {

    @GetMapping
    public Long getProcessId(){
        return ProcessHandle.current().pid();
    }

}
