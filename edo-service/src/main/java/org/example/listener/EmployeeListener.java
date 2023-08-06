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
    @RabbitListener(queues = "saveEmployee")
    public void receiveEmployee(EmployeeDto employeeDto) {
        log.info("Employee {} successful get from queue", employeeDto.getUsername());
        EmployeeDto saveEmployee = employeeService.saveEmployee(employeeDto);
        log.info("Saved Employee - {} successful!", saveEmployee.getUsername());
    }
}

