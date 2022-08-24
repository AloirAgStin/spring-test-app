package com.spring.test.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "app-support")
public interface AppSupportClient {

    @GetMapping(path = "/api/app-support")
    Long getPid();

}
