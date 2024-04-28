package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @PostMapping
    @ResponseBody
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) throws IOException {
        log.info("Creating task for employee: {}", taskForEmployeeDto.getTaskCreatorLastName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDate = dateTime.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=taskForEmployeeDto" + currentDate + ".pdf";
        headers.add(headerKey, headerValue);
        log.info("Создается PDF файл задания по резалюции {}", taskForEmployeeDto.getTaskCreatorFirstName());
        ByteArrayResource bis = taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto);
        log.info("Созданн PDF файл задания по резалюции {}", taskForEmployeeDto);

        return ResponseEntity
                .ok()
                .header(String.valueOf(headers))
                .body(bis);
    }

//    @PostMapping
//    public void convertTaskForEmployeeIntoPDF(HttpServletResponse response, @RequestBody TaskForEmployeeDto taskForEmployeeDto) throws IOException {
//        response.setContentType("application/pdf");
//        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//        String currentDate = dateTime.format(new Date());
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=taskForEmployeeDto" + currentDate + ".pdf";
//        response.setHeader(headerKey, headerValue);
//        log.info("Созданный PDF файл задания по резалюции {}", taskForEmployeeDto);
//        taskForEmployeeService.generateTaskForEmployeeIntoPDF(response, taskForEmployeeDto);
//    }
}
