package com.spring.test.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringTestEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestEurekaApplication.class, args);
    }

}
