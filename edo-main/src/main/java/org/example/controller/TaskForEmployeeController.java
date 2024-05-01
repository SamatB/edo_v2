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
import org.example.utils.FilePoolType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
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

    private final TaskForEmployeeClient taskForEmployeeClient;
    private final FileServiceImpl fileService;
    private final FileFeignClient fileFeignClient;
    private final KeycloakService keycloakService;


    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto, HttpServletRequest request) {
        EmployeeDto employeeDto = keycloakService.getEmployeeFromSessionUsername(request);
        log.info("employeeDto: {}", employeeDto);
//        taskForEmployeeDto.setTaskCreatorFirstName(employeeDto.getUsername());
        HttpHeaders headers = new HttpHeaders();
        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDate = dateTime.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=taskForEmployeeDto" + currentDate + ".pdf";
        headers.add(headerKey, headerValue);
        log.info("Before sending request");
        ByteArrayResource bis = taskForEmployeeClient.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);
        log.info("After sending request");

        return ResponseEntity
                .ok()
                .header(String.valueOf(headers))
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis);
    }

    @GetMapping("/get/{uuid}")
    public BufferedImage getFacsimileImage(@PathVariable String uuid, @RequestParam FilePoolType filetype) {
        uuid = taskForEmployeeClient.getFacsimileUUID();
        if (filetype == FilePoolType.FACSIMILE) {
            ResponseEntity<Resource> file = fileFeignClient.getFile(uuid);
            return taskForEmployeeClient.getFile(file.getBody());
        }
        return null;
    }

}
