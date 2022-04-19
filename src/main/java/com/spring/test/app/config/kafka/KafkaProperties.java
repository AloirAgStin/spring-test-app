package com.spring.test.app.config.kafka;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "spring-test-app.kafka")
public class KafkaProperties {

    private String bootstrapAddress;

    private String topic;

    private String groupId;

}
