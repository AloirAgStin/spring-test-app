package com.spring.test.app.controller;

import com.spring.test.app.config.kafka.KafkaTopicConfig;
import com.spring.test.app.model.kafka.KafkaMessageDto;
import com.spring.test.app.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@Validated
@RestController
@RequestMapping("/public-api/kafka")
@ConditionalOnBean(KafkaTopicConfig.class)
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    void postMessage(@RequestParam Instant date,  @RequestBody @Valid KafkaMessageDto kafkaMessageDto) {
        kafkaProducerService.postMessage(kafkaMessageDto);
    }

}
