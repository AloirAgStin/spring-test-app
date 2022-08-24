package com.spring.test.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "testFeign", url = "${spring-test-app.client.test-feign}")
public interface TestClient {

    @GetMapping(path = "/http/200")
    void testFeign();

}
