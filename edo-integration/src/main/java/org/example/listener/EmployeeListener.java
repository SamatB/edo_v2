package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmailService;
import org.example.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {
    private final EmployeeService employeeService;
    private final EmailService emailService;

    /**
     * Получает Коллекцию id EmployeeDTO с очереди rabbitmq и отправляет emails
     * на основе переданных идентификаторов.
     * @param employeeDtoId Коллекция id EmployeeDTO
     */
    @RabbitListener(queues = "employeeDtoId")
    public void sendEmailToEmployeeDtoId(Collection<Long> employeeDtoId) {
        try {
            log.info("Коллекция employeeDtoIDs успешно получен из очереди");
            employeeService.getEmailsByIds(employeeDtoId)
                    .parallelStream()
                    .forEach(emailService::sendEmail);
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }
    }
}

