/**
 * Контроллер для сохранения, получения, регистрации и архивации Appeal
 */

package org.example.controller;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.feign.AppealFeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Appeal")
public class AppealController {

    private final AppealFeignClient appealFeignClient;
    private final AtomicInteger countOfRequestsToAppeal = new AtomicInteger(0);

    /**
     * Возвращает обращение из базы данных.
     * Если обращение по-заданному id не найдено, возвращает ответ со статусом "Not Found".
     * Метод выполняет поиск обращения с помощью AppealFeignClient.
     *
     * @param id идентификатор вызываемого обращения.
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
     */
    @GetMapping("/{id}")
    @Operation(summary = "Возвращает обращение по идентификатору")
    public ResponseEntity<AppealDto> getAppeal(
            @Parameter(description = "Идентификатор обращения", required = true)
            @PathVariable Long id) {
        log.info("Начат поиск обращения с id: " + id + " ...");
        AppealDto appealDto = appealFeignClient.getAppeal(id);
        countOfRequestsToAppeal.incrementAndGet();
        if (appealDto == null) {
            log.warn("Ошибка получения обращения: обращение с id: " + id + " не найдено");
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
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
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
     * Если обращение по-заданному id не найдено, возвращает ответ со статусом "Not Found".
     * Метод выполняет сохранение обращения с помощью AppealFeignClient.
     *
     * @param id идентификатор архивируемого обращения.
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
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

    /**
     * Метод для регистрации обращения с указанным appealId.
     * Если обращение с указанным appealId не найдено, возвращает ответ со статусом "notFound".
     * Если обращение уже было зарегистрировано, возвращает ответ со статусом "badRequest".
     *
     * @param appealId идентификатор регистрируемого обращения.
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
     */
    @PatchMapping("/{appealId}/register")
    @Operation(summary = "Регистрирует обращение")
    public ResponseEntity<AppealDto> registerAppeal(
            @Parameter(description = "Идентификатор обращения", required = true)
            @PathVariable Long appealId) {
        log.info("Регистрация обращения с appealId " + appealId + " ...");
        try {
            AppealDto registeredAppeal = appealFeignClient.registerAppeal(appealId);
            log.info("Обращение c номером " + registeredAppeal.getNumber() + " зарегистрировано");
            return ResponseEntity.ok(registeredAppeal);
        } catch (FeignException.NotFound e) {
            log.warn("Ошибка получения обращения: обращение с id " + appealId + " не найдено");
            return ResponseEntity.notFound().build();
        } catch (FeignException.BadRequest e) {
            log.warn("Ошибка получения обращения: обращение с id " + appealId + " ранее зарегистрировано");
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Метод для резервирования номера обращения.
     * При попытке резервации номера для обращения с уже зарезервированным номером
     * или существующим номером,
     * или отсутствующим полем nomenclature
     * возвращается ответ со статусом "badRequest"
     *
     * @param appeal номер обращения, для которого необходимо резервировать номер.
     * @return ответ с Dto объектом обращения в виде ResponseEntity<AppealDto>
     */
    @PostMapping("/reserve-number")
    @Operation(summary = "Резервирует номер для обращения")
    public ResponseEntity<AppealDto> reserveNumberForAppeal(
            @Parameter(description = "Номер обращения", required = true)
            @RequestBody AppealDto appeal) {
        log.info("Резервирование номера для обращения... ");
        try {
            AppealDto registeredAppeal = appealFeignClient.reserveNumberForAppeal(appeal);
            if (registeredAppeal == null) {
                log.warn("Ошибка резервирования номера для обращения: обращение не должно быть null");
                return ResponseEntity.badRequest().build();
            }
            log.info("Для обращения зарезервирован номер: {}", registeredAppeal.getReservedNumber());
            return ResponseEntity.ok(registeredAppeal);
        } catch (FeignException.BadRequest e) {
            if (e.getSuppressed().length > 0) {
                log.warn(e.getSuppressed()[0].getMessage());
                return ResponseEntity.badRequest().build();
            }
            log.warn("Ошибка резервирования номера для обращения: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Метод для получения списка всех обращений.
     *
     * @return список обращений в виде ResponseEntity<List<AppealDto>>
     */
    @GetMapping("/all")
    @Operation(summary = "Получение списка обращений")
    public ResponseEntity<List<AppealDto>> getAllAppeals() {
        log.info("Получение списка обращений");
        List<AppealDto> appealDtos = appealFeignClient.getAllAppeals();
        if (appealDtos.isEmpty()) {
            log.warn("Ошибка получения списка обращений");
            return ResponseEntity.noContent().build();
        }
        log.info("Список обращений получен");
        return ResponseEntity.ok(appealDtos);
    }

    /**
     * Метод для получения файла обращений в формате XLSX.
     */
    @GetMapping("/export/excel")
    @Operation(summary = "Получение списка обращений в формате XLSX")
    public ResponseEntity<?> getAllAppealsAsXlsx() {
        log.info("Получение списка обращений в формате XLSX");
        try {
            byte[] file = appealFeignClient.downloadAppealsXlsxReport();
            if (file.length == 0) {
                log.warn("Ошибка получения списка обращений: список пустой");
                return ResponseEntity.notFound().build();
            }
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String filename = "appeals_" + currentDateTime + ".xlsx";
            log.info("Список обращений получен");
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .contentLength(file.length)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(file);
        } catch (Exception e) {
            log.warn("Ошибка получения списка обращений: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Метод для вывода в лог количества обращений к Appeal перед завершением приложения.
     *
     * @return кол-во обращений к Appeal
     */
    @PreDestroy
    public int logCountOfRequestsToAppeal() {
        log.info("Количество запросов на просмотр обращений: " + countOfRequestsToAppeal.get());
        return countOfRequestsToAppeal.get();
    }
}