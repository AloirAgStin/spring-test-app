package com.spring.test.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "testFeign", url = "${client.test-feign}")
public interface TestFeign {

    @GetMapping(path = "/http/200")
    void testFeign();

}
