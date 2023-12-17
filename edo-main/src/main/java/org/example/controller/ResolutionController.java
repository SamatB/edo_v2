package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.feign.ResolutionFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
