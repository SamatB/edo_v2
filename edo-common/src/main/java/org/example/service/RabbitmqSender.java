package org.example.service;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

/**
 * Интерфейс сендера сообщений RabbitMQ
 */
@Service
public interface RabbitmqSender {

    void send(String queue, Object dto);

    void send(String queue, Object dto, MessagePostProcessor messagePostProcessor);
}
