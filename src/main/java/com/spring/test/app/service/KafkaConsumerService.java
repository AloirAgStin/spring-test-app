package com.spring.test.app.service;

import com.spring.test.app.config.kafka.KafkaTopicConfig;
import com.spring.test.app.model.kafka.KafkaMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnBean(KafkaTopicConfig.class)
@RequiredArgsConstructor
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring-test-app.kafka.topic}", groupId = "${spring-test-app.kafka.group-id}")
    public void listen(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Payload KafkaMessageDto message) {
        log.info("Receive1-{}: {} from ", partition, message);
    }

}
