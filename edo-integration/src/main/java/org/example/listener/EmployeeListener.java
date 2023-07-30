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
     * @param employeeDtoId Коллекция id EmployeeDTO
     */
    @RabbitListener(queues = "employeeDtoId")
    public void receiveEmployeeDtoId(Collection<Long> employeeDtoId) {
        try {
            employeeService.getEmailToId(employeeDtoId)
                    .parallelStream()
                    .forEach(emailService::sendEmail);
            log.info("Коллекция employeeDtoIDs успешно получен из очереди");
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }
    }
}

