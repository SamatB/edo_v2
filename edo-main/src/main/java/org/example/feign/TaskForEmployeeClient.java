package org.example.feign;

import com.lowagie.text.Document;
import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "edo-service")
public interface TaskForEmployeeClient {

    @PostMapping("/task-for-employee")
    Document createTaskForEmployee(@RequestBody TaskForEmployeeDto task);
}
