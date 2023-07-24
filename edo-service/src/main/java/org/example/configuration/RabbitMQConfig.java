package org.example.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {
    @Bean
    public Queue departmentQueue() {
        return new Queue("department");
    }
}
