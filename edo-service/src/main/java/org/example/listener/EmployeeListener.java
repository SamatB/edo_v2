package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddressDto;
import org.example.dto.EmployeeDto;
import org.example.service.AddressService;
import org.example.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.example.configuration.RabbitConfiguration.SAVE_EMPLOYEE;

/**
 * Класс листенера под операции для Employee
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {
    private final EmployeeService employeeService;
    private final AddressService addressService;


    /**
     *
     * @param employeeDto - DTO сотрудника прилетающее из очереди на сохранение saveEmployee
     */
    @RabbitListener(queues = SAVE_EMPLOYEE)
    public void receiveEmployee(EmployeeDto employeeDto) {
        log.info("Employee {} successful get from queue", employeeDto.getUsername());
//        AddressDto savedAddressDto = addressService.saveAddress(employeeDto.getAddressDetails());
//        employeeDto.setAddressDetails(savedAddressDto);
        EmployeeDto saveEmployee = employeeService.saveEmployee(employeeDto);
        log.info("Saved Employee - {} successful!", saveEmployee.getUsername());
    }
}

