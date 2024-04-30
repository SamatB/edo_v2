package org.example.feign;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @PostMapping(value = "/task-for-employee", produces = MediaType.APPLICATION_PDF_VALUE)
    ByteArrayResource convertTaskForEmployeeIntoPDF(@RequestBody TaskForEmployeeDto task, @RequestHeader("Authorization") String authToken);

}
