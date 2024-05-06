package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.TaskForEmployeeDto;
import org.example.feign.FileFeignClient;
import org.example.feign.TaskForEmployeeClient;
import org.example.service.KeycloakService;
import org.example.service.impl.FileServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/task-for-employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task for employee")
public class TaskForEmployeeController {

    private final TaskForEmployeeClient taskForEmployeeClient;
    private final KeycloakService keycloakService;


    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto, HttpServletRequest request) {
        EmployeeDto employeeDto = keycloakService.getEmployeeFromSessionUsername(request);
        log.info("employeeDto: {}", employeeDto);

        log.info("Запущен процесс создания задания по резолюции в формате PDF");
        ByteArrayResource bis = taskForEmployeeClient.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);
        log.info("Файл задания по резолюции в формате PDF успешно создан");

        return ResponseEntity
                .ok()
                .body(bis);
    }

}
