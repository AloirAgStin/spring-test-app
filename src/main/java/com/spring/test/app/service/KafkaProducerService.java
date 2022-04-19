package com.spring.test.app.service;

import com.spring.test.app.config.kafka.KafkaProperties;
import com.spring.test.app.config.kafka.KafkaTopicConfig;
import com.spring.test.app.model.kafka.KafkaMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@ConditionalOnBean(KafkaTopicConfig.class)
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, KafkaMessageDto> kafkaTemplate;

    private final KafkaProperties properties;

    public void postMessage(KafkaMessageDto kafkaMessageDto) {
        var future = kafkaTemplate.send(properties.getTopic(),
                        UUID.randomUUID().toString(), kafkaMessageDto).completable()
                .thenAccept(this::onSuccess)
                .exceptionally(this::onError)
                .join();
    }

    private void onSuccess(SendResult<String, KafkaMessageDto> result) {
        log.info("Send {}", result);
    }

    private Void onError(Throwable ex) {
        log.info("Send error", ex);
        return null;
    }

}
