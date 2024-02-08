package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmployeeSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью Employee
 */
@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeSessionService employeeSessionService;

    /**
     * @return количество пользователей онлайн
     */
    @GetMapping("/online-users-number")
    @Operation(summary = "Возвращает количество пользователей онлайн")
    public ResponseEntity<Integer> getOnlineUsersNumber() {
        log.info("Подсчет пользователей онлайн");
        return ResponseEntity.ok(employeeSessionService.getOnlineUsersNumber());
    }
}
