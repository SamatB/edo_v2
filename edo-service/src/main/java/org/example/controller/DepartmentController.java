package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DepartmentDto;
import org.example.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для работы с сущностью Department.
 */
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Department")
public class DepartmentController {

    private final DepartmentService departmentService;


    /**
     * Поик департамента по имени.
     *
     * @param search строка символов для поиска департамента
     * @return возвращает список департаментов удовлетворяющих строке поиска, в случае удачного запроса.
     */

    @GetMapping("/search")
    @Operation(summary = "Возвращает список департаментов удовлетворяющих строке поиска")
    public ResponseEntity<List<DepartmentDto>> getDepartmentByName(@RequestParam String search) {
        try {
            log.info("Поиск пользователя в БД");
            return ResponseEntity.ok().body(departmentService.getDepartmentByName(search));
        } catch (Exception e) {
            log.warn("Ошибка поиска пользователя в БД");
            return ResponseEntity.badRequest().build();
        }
    }
}
