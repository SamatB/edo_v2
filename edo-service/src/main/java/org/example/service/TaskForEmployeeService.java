package org.example.service;

import org.example.dto.TaskForEmployeeDto;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    TaskForEmployeeDto generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task);
}
