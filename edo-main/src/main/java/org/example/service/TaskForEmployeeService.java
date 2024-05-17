package org.example.service;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.core.io.ByteArrayResource;

public interface TaskForEmployeeService {
    ByteArrayResource convertTaskForEmployeeIntoPDF(TaskForEmployeeDto taskForEmployeeDto);
}
