package org.example.feign;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @PostMapping(value = "/task-for-employee", produces = MediaType.APPLICATION_PDF_VALUE)//
    @ResponseBody
    ByteArrayResource convertTaskForEmployeeIntoPDF(@RequestBody TaskForEmployeeDto task);

//    @PostMapping("/task-for-employee")
//    void convertTaskForEmployeeIntoPDF(HttpServletResponse response, TaskForEmployeeDto taskForEmployeeDto);
}
