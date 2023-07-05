package org.example.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Класс ресивера сообщений RabbitMQ
 */
@EnableRabbit
@Component
public class RabbitmqListener {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqListener.class);

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void processMessage(Map<String,String> map) {
        logger.info(map.toString());
    }
}
