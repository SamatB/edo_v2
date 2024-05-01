package org.example.feign;

import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.security.Principal;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @PostMapping(value = "/task-for-employee", produces = MediaType.APPLICATION_PDF_VALUE)
    ByteArrayResource convertTaskForEmployeeIntoPDF(@RequestBody TaskForEmployeeDto task);

    @GetMapping("/task-for-employee/get-facsimile")
    BufferedImage getFile(Resource resource);

    @GetMapping("/task-for-employee/get-UUID")
    String getFacsimileUUID();

}
