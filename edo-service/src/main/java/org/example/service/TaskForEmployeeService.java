package org.example.service;

import com.lowagie.text.Document;
import org.example.dto.TaskForEmployeeDto;

import java.io.FileNotFoundException;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    Document generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) throws FileNotFoundException;
}
