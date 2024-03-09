package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.RabbitConfiguration;
import org.example.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.example.service.RabbitmqSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
@Tag(name = "Employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RabbitmqSender rabbitmqSender;

    /**
     * контроллер который принимает на вход коллекцию ID EmployeeDTO
     * и отправляет коллекцию в очередь
     *
     * @param idsEmployeeDTO Коллекция id пользователей.
     * @return ok
     */
    @PostMapping("/ids")
    @Operation(summary = "Получает коллекцию ID EmployeeDTO и отправляет в очередь")
    public ResponseEntity<String> sendEmployeeDtoId(@RequestBody Collection<Long> idsEmployeeDTO) {
        try {
            rabbitmqSender.send(RabbitConfiguration.EMPLOYEE_DTO_ID, idsEmployeeDTO);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении в очередь: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("employeeDtoId не добавилась в очередь");
    }

    /**
     * контроллер который принимает на вход коллекцию ID EmployeeDTO
     * и отправляет коллекцию в очередь
     *
     * @param idsEmployeeDTO Коллекция id пользователей.
     * @return Collection<String> emails Коллекцию email пользователей
     */

    @PostMapping("/emails")
    @Operation(summary = "Получает коллекцию ID EmployeeDTO и отплавляет коллекцию Emails")
    Collection<String> getEmailsByIds(@RequestBody List<Long> idsEmployeeDTO) {
        log.info("Коллекция idsEmployeeDTO успешно получен и Коллекция email отправлена в очередь");
        return employeeService.getEmployeesByIds(idsEmployeeDTO).stream()
                .map(EmployeeDto::getEmail)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    /**
     * Метод выполняет Обновление адреса для Employee.
     * Метод, которому можно передать адрес в виде строки "Ленина 1" и идентификатор
     * Пердусмотрен ввод ошибочных данных из-за забытой смены раскладки "Ktybyf" == "Ленина"
     *
     * @param id      идентификатор Employee.
     * @param address адрес в виде строки.
     * @return ответ обновление адреса у Employee
     */
    @PatchMapping("/{id}/update")
    EmployeeDto updateEmployeeAddress(String address, @PathVariable Long id) {
        log.info("Получен запрос на изменение адреса у Employee");
        return employeeService.updateEmployeeAddress(address, id);
    }

    /**
     * Метод принимает строку символов в качестве параметра и возвращает список объектов EmployeeDto.
     * Поиск данных начинается после ввода минимум трех символов
     * Пердусмотрен ввод ошибочных данных из-за забытой смены раскладки "Bdfyjd" == "Иванов"
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

        return ResponseEntity.ok(employeeService.getEmployeeSearchByText(name));
    }

}
