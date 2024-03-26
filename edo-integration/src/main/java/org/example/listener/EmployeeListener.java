package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmailDto;
import org.example.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.example.configuration.RabbitConfiguration.GET_EMAIL_DTO;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {
    private final EmailService emailService;

    /**
     * Получает EmailDTO с очереди rabbitmq и отправляет emails
     * на основе переданных идентификаторов.
     * @param emailDto содержит в себе список email сотрудников, тему и текст для рассылки сообщения
     */
    @RabbitListener(queues = GET_EMAIL_DTO)
    public void sendEmailToEmailDTO(EmailDto emailDto) {

        try {
            log.info("EmailDto успешно получен из очереди");
            emailDto.getEmail().stream().parallel().forEach(e -> emailService.sendEmail(e, emailDto.getSubject(), emailDto.getText()));
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }
    }
}

