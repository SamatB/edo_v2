package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {

    private final EmployeeService employeeService;
    @RabbitListener(queues = "employee")
    public void receiveEmployee(EmployeeDto employeeDto) {
        try {
            log.info("Работник успешно получен из очереди");
            employeeService.saveEmployee(employeeDto);
        } catch (Exception e) {
            log.error("Ошибка при сохранении в EmployeeListener: " + e.getMessage());
        }
    }
}

