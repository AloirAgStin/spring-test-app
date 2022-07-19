package com.spring.test.configserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@EnableScheduling
@Component
public class Ticker {

    @SneakyThrows
    @Scheduled(fixedDelay = 1000)
    public void test() {
        log.info(ProcessHandle.current().pid() + ", Config server: " + Instant.now() + ", " + Thread.currentThread().getId());
    }

}
