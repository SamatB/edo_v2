package org.example.controller;

import com.lowagie.text.Document;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@RestController
@RequestMapping("/task-for-employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task for employee")
public class TaskForEmployeeController {

    private TaskForEmployeeService taskForEmployeeService;

    @Autowired
    public TaskForEmployeeController(TaskForEmployeeService taskForEmployeeService) {
        this.taskForEmployeeService = taskForEmployeeService;
    }

    @PostMapping
    public void createTaskForEmployee(HttpServletResponse response, @RequestBody TaskForEmployeeDto taskForEmployeeDto) {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDate = dateTime.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=taskForEmployeeDto" + currentDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        try {
            log.info("Созданный PDF файл задания по резалюции {}", taskForEmployeeDto);
            this.taskForEmployeeService.generateTaskForEmployeeIntoPDF(response, taskForEmployeeDto);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Ошибка в генерации файла");
            System.out.println(e.getMessage());
        }
    }
}
