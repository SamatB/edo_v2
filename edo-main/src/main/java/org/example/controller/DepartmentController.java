package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DepartmentDto;
import org.example.dto.ResolutionDto;
import org.example.feign.DepartmentFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "department")
public class DepartmentController {

    private final DepartmentFeignClient departmentFeignClient;

    /**
     * Поик департамента по имени.
     *
     * @param search строка символов для поиска департамента
     * @return возвращает список департаментов удовлетворяющих строке поиска, в случае удачного запроса.
     */

    @GetMapping("/search")
    @Operation(summary = "Возвращает список департаментов удовлетворяющих строке поиска")
    public ResponseEntity<List<DepartmentDto>> getDepartmentByName(
            @Parameter(description = "Строка символов для поиска департамента", required = true)
            @RequestParam String search) {
        try {
            log.info("Отправлен запрос на поиск пользователя в БД");
            return ResponseEntity.ok().body(departmentFeignClient.getDepartmentByName(search).getBody());
        } catch (Exception e) {
            log.warn("Ошибка запроса на поиск пользователя в БД");
            return ResponseEntity.badRequest().build();
        }
    }
}
