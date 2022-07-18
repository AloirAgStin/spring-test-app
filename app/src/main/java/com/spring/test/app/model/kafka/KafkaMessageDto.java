package com.spring.test.app.model.kafka;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class KafkaMessageDto {

    private String code;

    private String data;

    private Instant time;

}
