package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.service.RabbitmqSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RabbitController {
    private final Logger logger = LoggerFactory.getLogger(RabbitController.class);
    private final RabbitmqSender rabbitmqSender;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Контроллер сообщений RabbitMQ
     */
    @PostMapping("/rabbit")
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, Object> dto) {
        if (dto.get("key") == null) {
            logger.info("Message is done but key of receiver is absent");
            return ResponseEntity.ok("Field \"key\" in JSON body is required");
        }
        if ((dto.get("key").toString()).equals("common")) {
            rabbitTemplate.setExchange("commonExchange");
            logger.info("Sending Message to the all Queues");
            rabbitTemplate.convertAndSend(dto);
        } else {
            rabbitTemplate.setExchange("directExchange");
            rabbitmqSender.send(dto.get("key").toString(),dto.get("dto"));
        }
        return ResponseEntity.ok("Message is done");
    }
}
