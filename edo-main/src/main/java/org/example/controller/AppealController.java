/**
 *  Контроллер для сохранения, получения и архивации Appeal
 */

package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.feign.AppealFeignClient;
import lombok.RequiredArgsConstructor;
import org.example.dto.AppealDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Appeal")
public class AppealController {

private final AppealFeignClient appealFeignClient;

/**
 * Возвращает обращение из базы данных.
 * Если обращение по заданному id не найдено, возвращает ответ со статусом "Not Found".
 * Метод выполняет поиск обращения с помощью AppealFeignClient.
 *
 * @param id идентификатор вызываемого обращения.
 * @return ответ с Dto объектом оборащения в виде ResponseEntity<AppealDto>
 *
 */
    @GetMapping("/{id}")
    @Operation(summary = "Возвращает обращение по идентификатору")
    public ResponseEntity<AppealDto> getAppeal(
            @Parameter(description = "Идентификатор обращения", required = true)
            @PathVariable Long id) {
        log.info("Начат поиск обращения с id: " + id + " ...");
        AppealDto appealDto = appealFeignClient.getAppeal(id);
        if (appealDto == null) {
            log.warn("Ошибка получечния обращения: обращение с id: " + id + " не найдено");
            return ResponseEntity.notFound().build();
        }
        log.info("Обращение c номером: " + appealDto.getNumber() + " успешно получено");
        return ResponseEntity.ok(appealDto);
    }

    /**
     * Сохраняет новое обращение в базу данных.
     * Если обращение равно null, то возвращается ответ со статусом "Bad Request"
     * Метод выполняет сохранение обращения с помощью AppealFeignClient.
     *
     * @param appealDto сохраненный объект DTO обращения.
     * @return ответ с Dto объектом оборащения в виде ResponseEntity<AppealDto>
     *
     */
    @PostMapping
    @Operation(summary = "Сохраняет новое обращение в базу данных")
    public ResponseEntity<AppealDto> saveAppeal(
            @Parameter(description = "Объект DTO обращения", required = true)
            @RequestBody AppealDto appealDto) {
        log.info("Сохранение нового обращения");
        AppealDto savedAppeal = appealFeignClient.saveAppeal(appealDto);
        if (savedAppeal == null) {
            log.warn("Ошибка сохранения обращения: обращение не должно быть null");
            return ResponseEntity.badRequest().build();
        }
        log.info("Обращение с номером: " + appealDto.getNumber() + " успешно сохранено");
        return ResponseEntity.ok(savedAppeal);
    }

    /**
     * Метод для добавления даты архивации обращения с указанным id.
     * Если обращение по заданному id не найдено, возвращает ответ со статусом "Not Found".
     * Метод выполняет сохранение обращения с помощью AppealFeignClient.
     *
     * @param id идентификатор архивируемого обращения.
     * @return ответ с Dto объектом оборащения в виде ResponseEntity<AppealDto>
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Архивирует обращение")
    public ResponseEntity<AppealDto> archiveAppeal(
            @Parameter(description = "Идентификатор обращения", required = true)
            @PathVariable Long id) {
        log.info("Архивация обращения с id " + id + " ...");
        AppealDto archivedAppeal = appealFeignClient.archiveAppeal(id);
        if (archivedAppeal == null) {
            log.warn("Ошибка архивации: обращение с id: " + id + " не найдено");
            return ResponseEntity.notFound().build();
        }
        log.info("Обращение номер: " + archivedAppeal.getNumber() + " успешно заархивировано");
        return ResponseEntity.ok(archivedAppeal);
    }
}