package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.feign.EmployeeFeignClient;
import org.example.service.EmployeeSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Контроллер для работы с сущностью Employee
 */
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeSessionService employeeSessionService;
    private final EmployeeFeignClient employeeFeignClient;

    /**
     * @return количество пользователей онлайн
     */
    @GetMapping("/online-users-number")
    @Operation(summary = "Возвращает количество пользователей онлайн")
    public ResponseEntity<Integer> getOnlineUsersNumber() {
        log.info("Подсчет пользователей онлайн");
        return ResponseEntity.ok(employeeSessionService.getOnlineUsersNumber());
    }

    /**
     * Метод принимает строку символов в качестве параметра и возвращает список объектов EmployeeDto.
     * Поиск данных начинается после ввода минимум трех символов
     * Предусмотрен ввод ошибочных данных из-за забытой смены раскладки "Bdfyjd" == "Иванов"
     * Предусмотренно, что "ё"=="е"
     *
     * @param name Строка с символами, представляющими имя для поиска сотрудников.
     * @return список EmployeeDto отсортированных по фамилии - по убыванию
     * @throws IllegalArgumentException В случае, если введено менее трех символов в качестве параметра
     */
    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getEmployeeSearchByText(@RequestParam(name = "name", required = false) String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Строка name пуста или меньше трех символов");
        }

        return ResponseEntity.ok(employeeFeignClient.getEmployeeSearchByText(name));
    }
}
