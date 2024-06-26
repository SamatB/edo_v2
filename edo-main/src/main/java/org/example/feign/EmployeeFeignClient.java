/**
 * Клиент для работы с методами, связанными с Employee в сервисе edo-service.
 */

package org.example.feign;

import org.example.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "edo-service", path = "/employee")
public interface EmployeeFeignClient {

    @GetMapping("/{username}")
    EmployeeDto getByUsername(@PathVariable(name = "username") String username);

    @GetMapping("")
    List<EmployeeDto> getEmployeeSearchByText(@RequestParam(name = "name", required = false) String name);
}
