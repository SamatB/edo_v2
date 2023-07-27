package org.example.service;
/**
 * Интерфейс сендера сообщений RabbitMQ
 */
public interface RabbitmqSender {
    void send(String queue, Object dto);
}
