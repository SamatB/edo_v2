package org.example.service;

import org.example.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto getEmployeeByUsername(String username);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getEmployeesByIds(List<Long> ids);
}
