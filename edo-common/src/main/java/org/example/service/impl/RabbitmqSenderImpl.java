package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.service.RabbitmqSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Имплемент RabbitmqSender
 */
@Service
@Getter
@Setter
@AllArgsConstructor
public class RabbitmqSenderImpl implements RabbitmqSender {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqSenderImpl.class);
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void send(String queue, Object dto) {
        try {
            rabbitTemplate.convertAndSend(queue, dto);
            logger.info("Sending Message to the Queue : " + queue);
        } catch (Exception e) {
            System.out.println("Sending error Message to the Queue : " + e.getMessage());
        }
    }
}
