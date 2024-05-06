package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String currentDate = dateTime.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "inline; filename=taskForEmployeeDto" + currentDate + ".pdf";
            headers.add(headerKey, headerValue);
            log.info("Создается PDF файл задания по резалюции c UUID {}", taskForEmployeeDto.getUuid());
            ByteArrayResource bis = taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto);
            log.info("Создан PDF файл задания по резалюции c UUID {}", taskForEmployeeDto.getUuid());
            return ResponseEntity
                    .ok()
                    .header(String.valueOf(headers))
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bis);
        } catch (IOException e) {
            log.warn("Не удалось создать PDF файл задания, ошибка: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
