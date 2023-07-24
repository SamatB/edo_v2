package org.example.controllers;

import org.example.service.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {
    private final Logger logger = LoggerFactory.getLogger(RabbitController.class);
    private final RabbitMQSender rabbitmqSendler;

    RabbitController(RabbitMQSender rabbitmqSendler) {
        this.rabbitmqSendler = rabbitmqSendler;
    }
    /**
     * Контроллер сообщений RabbitMQ
     */
    @PostMapping("/rabbit")
    public ResponseEntity<String> receiveMessage(@RequestBody Object dto,
                                                 @RequestParam String queue) {
        if (queue == null) {
            logger.info("Message is done but it is no name of queue");
            return ResponseEntity.ok("Parameter \"queue\" is required");
        }
        if (queue.equals("common")) {
            rabbitmqSendler.getRabbitTemplate().setExchange("commonExchange");
            logger.info("Sending Message to the all Queues");
            rabbitmqSendler.getRabbitTemplate().convertAndSend(queue, dto);
        } else {
            rabbitmqSendler.getRabbitTemplate().setExchange("directExchange");
            rabbitmqSendler.send(queue, dto);
        }
        return ResponseEntity.ok("Message is done");
    }
}
