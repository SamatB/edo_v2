package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Класс сендера сообщений RabbitMQ
 */
@Service
@Getter
@Setter
@AllArgsConstructor
public class RabbitmqSender {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqSender.class);
    private RabbitTemplate rabbitTemplate;
    public void send(String queue, Object dto) {
        try {
            rabbitTemplate.convertAndSend(queue, dto);
            logger.info("Sending Message to the Queue : " + queue);
        } catch (Exception e) {
            System.out.println("Sending error Message to the Queue : " + e.getMessage());
        }
    }
}
