package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.service.ResolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Контроллеры для работы с сущностью Resolution.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/resolutions")
@Tag(name = "Resolution")
public class ResolutionController {

    private final ResolutionService resolutionService;

    /**
     * Получение List всех резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<ResolutionDto>> getAll() {
        log.info("Получение всех резолюций");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(null);
            log.info("Список резолюций получен");
            return ResponseEntity.ok(resolutionList);
        } catch (Exception e) {
            log.error("Возникла ошибка поиска всех резолюций");
            return ResponseEntity.status(502).build();
        }
    }


    /**
     * Получение List архивных резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/archived")
    public ResponseEntity<?> getArchived() {
        log.info("Получение архивных резолюций");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(true);
            if (resolutionList != null) {
                log.info("Список архивных резолюций получен");
                return ResponseEntity.ok(resolutionList);
            } else {
                log.info("Нет резолюций в архиве");
                return ResponseEntity.ok("Нет резолюций в архиве");
            }
        } catch (Exception e) {
            log.error("Возникла ошибка поиска архивных резолюций");
            return ResponseEntity.status(502).build();
        }
    }

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionDto объект DTO резолюции
     * @return сохраненный объект resolutionDto со статусом 200 в случае успешного выполнения
     * и статусом 400 в случае не удачи.
     */

    @PostMapping
    @Operation(summary = "Сохраняет новую резолюцию в базе данных")
    public ResponseEntity<?> saveResolution(
            @Parameter(description = "Объект DTO резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {
        try {
            log.info("Сохранение новой резолюции");
            return ResponseEntity.ok().body(resolutionService.saveResolution(resolutionDto));
        } catch (Exception e) {
            log.error("Ошибка сохранения резолюции в БД '\n"  + e.getMessage());
            return ResponseEntity.badRequest().body(e);
        }

    }

    /**
     * Получение List резолюций без архивных
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/withoutArchived")
    public ResponseEntity<?> withoutArchived() {
        log.info("Получение всех резолюций, кроме архивных");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(false);
            if (resolutionList != null) {
                log.info("Список всех резолюций, кроме архивных получен");
                return ResponseEntity.ok(resolutionList);
            } else {
                log.info("Все резолюции в архиве");
                return ResponseEntity.ok("Все резолюции в архиве");
            }
        } catch (Exception e) {
            log.error("Возникла ошибка поиска всех резолюций, кроме архивных");
            return ResponseEntity.status(502).build();
        }
    }


    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект resolutionDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает резолюцию по идентификатору")
    public ResponseEntity<?> getResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id) {
        try {
            log.info("Поиск резолюции по идентификатору");
            return ResponseEntity.ok().body(resolutionService.getResolution(id));
        } catch (Exception e) {
            log.error("Ошибка поиска резолюции  по идентификатору в БД");
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект resolutionDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */

    @PutMapping("/{id}/archive")
    @Operation(summary = "Переносит резолюцию в архив")
    public ResponseEntity<?> archiveResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id) {

        try {
            log.info("Архивация резолюции в БД");
            return ResponseEntity.ok().body(resolutionService.archiveResolution(id));
        } catch (Exception e) {
            log.error("Ошибка архивации резолюции в БД");
            return ResponseEntity.badRequest().body(e);
        }
    }


    /**
     * Обновляет данные резолюции.
     *
     * @param id            идентификатор резолюции
     * @param resolutionDto объект DTO с новыми данными резолюции
     * @return обновленный объект resolutionDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */

    @PutMapping("/{id}")
    @Operation(summary = "Обновляет данные резолюции")
    public ResponseEntity<?> updateResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id,
            @Parameter(description = "Объект DTO с новыми данными резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {

        try {
            log.info("Обновление резолюции в БД");
            return ResponseEntity.ok().body(resolutionService.updateResolution(id, resolutionDto));
        } catch (Exception e) {
            log.error("Ошибка обновления резолюции в БД");
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     * Проверяет корректность полей резолюции
     * Проверяемые поля: type, serialNumber, signerId
     *
     * @param resolutionDto резолюция
     * @return имена и описания некорректно заполненных полей resolutionDto
     */
    @PostMapping("/validate")
    @Operation(summary = "Проверяет корректность полей резолюции: type, serialNumber, signerId")
    public ResponseEntity<String> validateResolution(
            @Parameter(description = "Объект DTO резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {
        log.info("Валидация резолюции");
        try {
            resolutionService.validateResolution(resolutionDto);
            log.info("Ошибки не обнаружены");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
