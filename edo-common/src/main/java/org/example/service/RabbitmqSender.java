package org.example.service;

import org.springframework.stereotype.Service;

/**
 * Интерфейс сендера сообщений RabbitMQ
 */
@Service
public interface RabbitmqSender {

    org.springframework.amqp.rabbit.core.RabbitTemplate getRabbitTemplate();

    void send(String queue, Object dto);
}
