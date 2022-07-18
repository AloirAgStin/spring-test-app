package com.spring.test.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties(prefix = "spring-test-app")
public class ApplicationProperties {

    @NotEmpty
    private String editableValue;

}
