package org.example.service;

import org.example.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeByUsername(String username);

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);
}
