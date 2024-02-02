package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.service.AppealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с сущностью Appeal.
 */
@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Appeal")
public class AppealController {

    private final AppealService appealService;


    /**
     * Сохраняет новое обращение в базу данных.
     * При ошибке сохранения возвращается ответ со статусом "Bad Request"
     *
     * @param appeal сохраненный объект DTO факсимиле.
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
     */
    @PostMapping
    @Operation(summary = "Сохраняет новое обращение в базу данных")
    ResponseEntity<AppealDto> saveAppeal(@RequestBody AppealDto appeal) {
        log.info("Сохранение обращения");
        try {
            AppealDto savedAppeal = appealService.saveAppeal(appeal);
            log.info("Обращение успешно сохранено. id обращения " + savedAppeal.getId());
            return ResponseEntity.ok(savedAppeal);
        } catch (Exception e) {
            log.error("Ошибка сохранения обращения: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает обращение по id.
     * При ошибке получения возвращается ответ со статусом "Not Found"
     *
     * @param appealId идентификатор обращения
     * @return объект DTO обращения
     */
    @GetMapping("/{appealId}")
    @Operation(summary = "Получение обращения по id")
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long appealId) {
        log.info("Получение обращения с id " + appealId);
        try {
            AppealDto appealDto = appealService.getAppeal(appealId);
            log.info("Получено обращение с id " + appealId);
            return ResponseEntity.ok(appealDto);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Регистрирует обращение по id.
     * При ошибке получения обращения возвращается ответ со статусом "notFound"
     * При попытке регистрации уже зарегистрированного обращения возвращается ответ со статусом "badRequest"
     *
     * @param appealId идентификатор обращения.
     * @return объект DTO обращения в случае успешной регистрации.
     */
    @PostMapping("/{appealId}/register")
    @Operation(summary = "Регистрация обращения по id")
    public ResponseEntity<AppealDto> registerAppealById(@PathVariable Long appealId) {
        log.info("Получение обращения с id " + appealId);
        try {
            AppealDto appealDto = appealService.registerAppeal(appealId);
            log.info("Зарегистрировано обращение с id " + appealId);
            return ResponseEntity.ok(appealDto);
        } catch (EntityNotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (EntityExistsException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
