package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;


@FeignClient(name = "edo-service")
public interface EmployeeFeignClient {
    /**
     * Метод для получения emails по ids
     */
    @GetMapping("/employee/emails")
    Collection<String> getByEmails(@RequestBody Collection<Long> id);
}
