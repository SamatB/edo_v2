package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.example.service.RabbitmqSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Objects;

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
    @Operation(summary = "Получает коллекцию ID EmployeeDTO")
    public ResponseEntity<String> sendEmployeeDtoId(@RequestBody Collection<Long> idsEmployeeDTO) {
        try {
            log.info("Отправление коллекции ID EmployeeDTO в очередь");
            rabbitmqSender.send("employeeDtoId", idsEmployeeDTO);
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
    Collection<String> getByEmails(@RequestBody Collection<Long> idsEmployeeDTO) {
        log.info("Коллекция idsEmployeeDTO успешно получен и Коллекция email отправлена в очередь");
        return idsEmployeeDTO.stream()
                .map(employeeService::getEmployeeById)      // получаем employee
                .map(Employee::getEmail)                    // получаем email
                .filter(s -> !s.isEmpty())                  // убираем null&пустые строки
                .toList();
    }
}
