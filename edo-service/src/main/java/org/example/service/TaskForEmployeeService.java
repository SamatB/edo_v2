package org.example.service;

import com.lowagie.text.Document;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.TaskForEmployeeDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpResponse;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    void generateTaskForEmployeeIntoPDF(HttpServletResponse httpResponse, TaskForEmployeeDto task) throws IOException;
}
