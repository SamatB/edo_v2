/**
 * Клиент для работы с методами, связанными с Employee в сервисе edo-service.
 */

package org.example.feign;

import org.example.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "edo-service")
public interface EmployeeFeignClient {

    @GetMapping("/employee")
    public EmployeeDto getByUsername(@RequestParam String username);
}
