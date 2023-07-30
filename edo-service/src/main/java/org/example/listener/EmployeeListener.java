package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "employee")
public class EmployeeListener {

    private final EmployeeService employeeService;
    @RabbitHandler
    public void receiveEmployee(EmployeeDto employeeDto) {
        try {
            log.info("Работник успешно получен из очереди");
            employeeService.saveEmployee(employeeDto);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }
    }
}