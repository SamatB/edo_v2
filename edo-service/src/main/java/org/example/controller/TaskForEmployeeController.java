package org.example.controller;

import com.lowagie.text.Document;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
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

    private final TaskForEmployeeService taskForEmployeeService;

    @PostMapping
    public ResponseEntity<Document> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) throws FileNotFoundException {
        try {
            com.lowagie.text.Document task = taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto);
            log.info("Созданный PDF файл задания по резалюции {}", taskForEmployeeDto);
            return ResponseEntity.ok(task);
        } catch (FileNotFoundException e) {
            log.info("Ошибка в генерации файла");
            return ResponseEntity.notFound().build();
        }
    }
}
