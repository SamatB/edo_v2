package org.example.feign;

import feign.Response;
import org.example.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Клиент для работы с сервисом edo-service, сущностью Department.
 */

@FeignClient(name = "edo-service", path = "/department")
public interface DepartmentFeignClient {

    /**
     * Поик департамента по имени.
     *
     * @param search строка символов для поиска департамента
     * @return возвращает список департаментов удовлетворяющих строке поиска
     */

    @GetMapping("/search")
    ResponseEntity<List<DepartmentDto>> getDepartmentByName(@RequestParam String search);
}
