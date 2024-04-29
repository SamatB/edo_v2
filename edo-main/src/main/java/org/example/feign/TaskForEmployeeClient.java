package org.example.feign;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @PostMapping(value = "/task-for-employee", produces = MediaType.APPLICATION_PDF_VALUE)//
    @ResponseBody
    ByteArrayResource convertTaskForEmployeeIntoPDF(@RequestBody TaskForEmployeeDto task);

}
