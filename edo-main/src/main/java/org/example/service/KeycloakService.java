package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.EmployeeDto;

public interface KeycloakService {
    EmployeeDto getEmployeeFromSessionUsername();
}
