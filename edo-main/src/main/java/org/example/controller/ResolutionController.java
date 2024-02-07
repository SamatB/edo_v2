package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.feign.ResolutionFeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с сущностью Resolution.
 */
@RestController
@RequestMapping("/resolutions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Resolutions")
public class ResolutionController {

    private final ResolutionFeignClient resolutionFeignClient;

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @PostMapping
    @Operation(summary = "Сохраняет новую резолюцию в базе данных")
    public ResponseEntity<ResolutionDto> saveResolution(
            @Parameter(description = "Объект DTO резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {
        log.info("Saving new resolution");
        ResolutionDto savedResolutionDto = resolutionFeignClient.saveResolution(resolutionDto);
        return ResponseEntity.ok(savedResolutionDto);
    }

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект DTO резолюции
     */
    @PutMapping("/{id}/archive")
    @Operation(summary = "Переносит резолюцию в архив")
    public ResponseEntity<ResolutionDto> archiveResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id) {
        log.info("Archiving resolution with id {}", id);
        ResolutionDto updatedResolutionDto = resolutionFeignClient.archiveResolution(id);
        if (updatedResolutionDto == null) {
            log.warn("Resolution with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedResolutionDto);
    }

    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект DTO резолюции
     */
    @GetMapping("/{id}")
    @Operation(summary = "Возвращает резолюцию по идентификатору")
    public ResponseEntity<ResolutionDto> getResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id) {
        log.info("Getting resolution with id {}", id);
        ResolutionDto resolutionDto = resolutionFeignClient.getResolution(id);
        if (resolutionDto == null) {
            log.warn("Resolution with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resolutionDto);
    }

    /**
     * Обновляет данные резолюции.
     *
     * @param id идентификатор резолюции
     * @param resolutionDto объект DTO с новыми данными резолюции
     * @return обновленный объект DTO резолюции
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновляет данные резолюции")
    public ResponseEntity<ResolutionDto> updateResolution(
            @Parameter(description = "Идентификатор резолюции", required = true)
            @PathVariable Long id,
            @Parameter(description = "Объект DTO с новыми данными резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {
        log.info("Updating resolution with id {}", id);
        ResolutionDto updatedResolutionDto = resolutionFeignClient.updateResolution(id, resolutionDto);
        if (updatedResolutionDto == null) {
            log.warn("Resolution with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedResolutionDto);
    }

    /**
     * Получение List всех резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/find")
    private ResponseEntity<List<ResolutionDto>> getAll(@RequestParam(name = "archivedStatus") Boolean archived){

        if (archived == null) {
            log.info("Получение всех резолюций");
        } else {
            log.info("Получение {} резолюций", (archived ? "архивных" : "не архивных"));
        }

        try {
            List<ResolutionDto> resolutionDtoList = resolutionFeignClient.findResolutions(archived);
            log.info("Список резолюций получен");
            return ResponseEntity.ok(resolutionDtoList);
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска резолюций");
            return ResponseEntity.status(502).build();
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
    @Operation(summary = "Проверяет корректность полей резолюции")
    public ResponseEntity<String> validateResolution(
            @Parameter(description = "Объект DTO резолюции", required = true)
            @RequestBody ResolutionDto resolutionDto) {
        log.info("Валидация резолюции");
        try {
            resolutionFeignClient.validateResolution(resolutionDto);
            log.info("Ошибки не выявлены.");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            message = message.substring(message.lastIndexOf('[') + 1, message.lastIndexOf(']'));
            log.error(message);
            return ResponseEntity.badRequest().body(message);
        }
    }
}
