package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.TaskForEmployeeDto;
import org.example.feign.TaskForEmployeeFeignClient;
import org.example.service.KeycloakService;
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

    private final TaskForEmployeeFeignClient taskForEmployeeFeignClient;
    private final KeycloakService keycloakService;

    /**
     * Данный метод принимает на вход TaskForEmployeeDto и обрабатывается единственным методом TaskForEmployeeFeignClient,
     * в теле ответа отправляется созданный PDF файл формата А4
     *
     * @param taskForEmployeeDto - запрос, который нужно обратотать.
     * @return - ответ, который содержит созданный PDF файл в виде ByteArrayResource в обертке ResponseEntity,
     * созданный файл открывестя в браузере.
     */
    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Отправляет сформированный PDF файл задания формата А4")
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) {
        EmployeeDto employeeDto = keycloakService.getEmployeeFromSessionUsername();
        log.info("employeeDto: {}", employeeDto);
        taskForEmployeeDto.setTaskCreatorFirstName(employeeDto.getFirstName());
        taskForEmployeeDto.setTaskCreatorLastName(employeeDto.getLastName());
        taskForEmployeeDto.setTaskCreatorMiddleName(employeeDto.getMiddleName());
        log.info("Запущен процесс создания задания по резолюции в формате PDF");
        ByteArrayResource bis = taskForEmployeeFeignClient.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);
        log.info("Файл задания по резолюции в формате PDF успешно создан");

        return ResponseEntity
                .ok()
                .body(bis);
    }

}
