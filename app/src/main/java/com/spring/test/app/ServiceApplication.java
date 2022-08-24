package com.spring.test.app;

import com.spring.test.app.config.ApplicationProperties;
import com.spring.test.app.config.kafka.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@RefreshScope //reloads the custom Spring properties you have in your application configuration.
// Items like your database configuration used by Spring Data won’t be reloaded by this annotation
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(value = {KafkaProperties.class, ApplicationProperties.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
