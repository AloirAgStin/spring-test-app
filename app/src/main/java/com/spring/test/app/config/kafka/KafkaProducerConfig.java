package com.spring.test.app.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.test.app.model.kafka.KafkaMessageDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(KafkaTopicConfig.class)
public class KafkaProducerConfig {
    private final KafkaProperties properties;

    private final ObjectMapper objectMapper;

    @Bean
    public ProducerFactory<String, KafkaMessageDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapAddress());
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<KafkaMessageDto>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, KafkaMessageDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
