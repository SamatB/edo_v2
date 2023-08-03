package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;


@FeignClient(name = "edo-service")
public interface EmployeeFeignClient {
    /**
     * Метод для получения emails по ids
     */
    @PostMapping("/employee/emails")
    Collection<String> getEmailsByIds(@RequestBody Collection<Long> id);
}

