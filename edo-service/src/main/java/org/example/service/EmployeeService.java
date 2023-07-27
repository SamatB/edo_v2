package org.example.service;

import org.example.dto.EmployeeDto;
import org.example.entity.Employee;

public interface EmployeeService {
    EmployeeDto getEmployeeByUsername(String username);

    Employee getEmployeeById(Long id);
}
