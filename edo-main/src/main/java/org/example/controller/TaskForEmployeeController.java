package org.example.controller;

import com.lowagie.text.Document;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.feign.TaskForEmployeeClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/task-for-employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task for employee")
public class TaskForEmployeeController {

    private final TaskForEmployeeClient taskForEmployeeClient;

    @PostMapping
    public ResponseEntity<Document> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) {
        Document task = taskForEmployeeClient.createTaskForEmployee(taskForEmployeeDto);
        log.info("Созданный PDF файл задания по резалюции {}", taskForEmployeeDto);
        return ResponseEntity.ok(task);
    }
}
