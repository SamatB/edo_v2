package org.example.controllers;

import org.example.service.RabbitmqSendler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RabbitController {
    private final Logger logger = LoggerFactory.getLogger(RabbitController.class);
    private final RabbitmqSendler rabbitmqSendler;

    RabbitController(RabbitmqSendler rabbitmqSendler) {
        this.rabbitmqSendler = rabbitmqSendler;
    }
    /**
     * Контроллер сообщений RabbitMQ
     */
    @PostMapping("/rabbit")
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, String> map) {
        if (map.get("key") == null) {
            logger.info("Message is done but key of receiver is absent");
            return ResponseEntity.ok("Field \"key\" in JSON body is required");
        }
        if (map.get("key").equals("common")) {
            rabbitmqSendler.getRabbitTemplate().setExchange("commonExchange");
            logger.info("Sending Message to the all Queues");
            rabbitmqSendler.getRabbitTemplate().convertAndSend(map);
        } else {
            rabbitmqSendler.getRabbitTemplate().setExchange("directExchange");
            rabbitmqSendler.send(map);
        }
        return ResponseEntity.ok("Message is done");
    }
}
