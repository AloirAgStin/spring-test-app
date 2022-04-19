package com.spring.test.app.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.test.app.model.kafka.KafkaMessageDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(KafkaTopicConfig.class)
public class KafkaConsumerConfig {

    private final KafkaProperties properties;

    private final ObjectMapper objectMapper;

    @Bean
    public ConsumerFactory<String, KafkaMessageDto> consumerFactory() {
        JsonDeserializer<KafkaMessageDto> deserializer = new JsonDeserializer<>(objectMapper);
        deserializer.addTrustedPackages("*");

        var configProps = new HashMap<String, Object>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapAddress());
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessageDto> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, KafkaMessageDto>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
