package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Класс сендлера сообщений RabbitMQ
 */
@Service
@Getter
@Setter
@AllArgsConstructor
public class RabbitMQSender {
    private final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);
    private RabbitTemplate rabbitTemplate;
    public void send(String queue, Object dto) {
        rabbitTemplate.convertAndSend(queue, dto);
        logger.info("Sending Message to the Queue : " + queue);
    }
}
