package org.example.publisher;

import lombok.RequiredArgsConstructor;
import org.example.dto.EmailDto;
import org.example.service.RabbitmqSender;
import org.springframework.stereotype.Component;

/**
 * Класс публикует в очередь RabbitMQ листа согласования
 */

@Component
@RequiredArgsConstructor
public class AgreementListPublisher {
    private final RabbitmqSender rabbitmqSender;
    public static final String SEND_AGREEMENT_LIST_ROUTING_KEY = "sendAgreementList";

    public void sendAgreementListEmailNotification(EmailDto emailDto, Long priority) {
        rabbitmqSender.send(SEND_AGREEMENT_LIST_ROUTING_KEY, emailDto, message -> {
            message.getMessageProperties().setPriority(getPriority(priority));
            return message;
        });
    }

    /**
     * Метод принимает номер участника и преобразует его в приоритет в очереди
     * @param priority номер участника
     * @return приоритет в очереди
     */
    private Integer getPriority(Long priority) {
        int maxQueueSize = 255;
        if (priority > maxQueueSize) {
            return 1;
        } else if (priority < 1) {
            return 0;
        } else {
            return maxQueueSize + 1 - priority.intValue();
        }
    }
}
