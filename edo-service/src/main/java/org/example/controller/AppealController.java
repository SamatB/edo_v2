package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.service.AppealService;
import org.example.service.ReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * Резервирует номер для обращения.
     * При попытке резервации номера для обращения с уже зарезервированным номером
     * или существующим номером,
     * или отсутствующим полем nomenclature
     * возвращается ответ со статусом "badRequest"
     *
     * @param appeal номер обращения.
     * @return объект DTO обращения в случае успешной резервации.
     */
    @PostMapping("/reserve-number")
    @Operation(summary = "Резервирование номера для обращения")
    public ResponseEntity<AppealDto> reserveNumberForAppeal(@RequestBody AppealDto appeal) {
        log.info("Резервирование номера  для обращения...");

        try {
            AppealDto appealDto = appealService.reserveNumberForAppeal(appeal);
            log.info("Номер {} успешно зарезервирован", appealDto.getReservedNumber() != null
                    ? appealDto.getReservedNumber()
                    : null);
            return ResponseEntity.ok(appealDto);
        } catch (EntityExistsException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/export/csv")
    @Operation(summary = "Экспорт обращений в CSV")
    public ResponseEntity<Resource> downloadAppealsCsvReport() {
        log.info("Экспорт обращений в CSV");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "appeals_" + currentDateTime + ".csv";
        try {
            ByteArrayResource file = new ByteArrayResource(reportService.downloadAppealsCsvReport().readAllBytes());
            if (file.contentLength() == 0) {
                log.warn("Ошибка получения списка обращений: файл не существует");
                return ResponseEntity.notFound().build();
            }
            log.info("Список обращений отправлен");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.contentLength())
                    .body(file);
        } catch (Exception e) {
            log.warn("Ошибка получения списка обращений: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
