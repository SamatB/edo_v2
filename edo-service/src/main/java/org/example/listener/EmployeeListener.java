package org.example.listener;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import static org.example.configuration.RabbitConfiguration.SAVE_EMPLOYEE_ROUTING_KEY;

/**
 * Класс листенера под операции для Employee
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {
    private final EmployeeService employeeService;


    /**
     *
     * @param employeeDto - DTO сотрудника прилетающее из очереди на сохранение saveEmployee
     */
    @RabbitListener(queues = SAVE_EMPLOYEE_ROUTING_KEY)
    public void receiveEmployee(EmployeeDto employeeDto) {
        log.info("Employee {} successful get from queue", employeeDto.getUsername());
            EmployeeDto saveEmployee = employeeService.saveEmployee(employeeDto);
            log.info("Saved Employee - {} successful!", saveEmployee.getUsername());
    }
}

