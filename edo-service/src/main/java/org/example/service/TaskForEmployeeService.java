package org.example.service;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) throws IOException;
//    void generateTaskForEmployeeIntoPDF(HttpServletResponse response, TaskForEmployeeDto task) throws IOException;
}
