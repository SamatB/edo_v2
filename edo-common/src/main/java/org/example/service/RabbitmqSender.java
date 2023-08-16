package org.example.service;

import org.springframework.stereotype.Service;

/**
 * Интерфейс сендера сообщений RabbitMQ
 */
@Service
public interface RabbitmqSender {

    void send(String queue, Object dto);
}