package org.example.feign;

import com.lowagie.text.Document;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @GetMapping("/task-for-employee")
    void createTaskForEmployee(@RequestParam HttpServletResponse response, @RequestBody TaskForEmployeeDto task);
}
