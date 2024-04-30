package org.example.service;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.security.Principal;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task, String authToken) throws IOException;
}
