package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с TaskForEmployeeDto - задание по резалюции сотруднику.
 */
@RestController
@RequestMapping("/task-for-employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task for employee")
public class TaskForEmployeeController {

    private final TaskForEmployeeService taskForEmployeeService;

    /**
     * Данный метод обрабатывается методом TaskForEmployeeService, подробное описание метода в TaskForEmployeeService.
     */
    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Отправляет сформированный PDF файл задания формата А4")
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) {
        log.info("Запущен процесс создания задания по резолюции в формате PDF");
        ByteArrayResource bis = taskForEmployeeService.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);
        log.info("Файл задания по резолюции в формате PDF успешно создан");

        return ResponseEntity
                .ok()
                .body(bis);
    }
}
