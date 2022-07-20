package com.spring.test.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    //todo config server map to docker container

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
