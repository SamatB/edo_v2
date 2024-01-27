package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionMapper;
import org.example.service.ResolutionService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/resolutions")
public class ResolutionController {

    private final ResolutionService resolutionService;


    /**
     * Получение List всех резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/all")
    private ResponseEntity<Collection<ResolutionDto>> getAll(){
        log.info("Получение всех резолюций");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(null);
            log.info("Список резолюций получен");
            return ResponseEntity.ok(resolutionList);
        }
        catch (Exception e){
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
    private ResponseEntity<?> getArchived(){
        log.info("Получение архивных резолюций");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(true);
            if (resolutionList != null){
                log.info("Список архивных резолюций получен");
                return ResponseEntity.ok(resolutionList);
            }
            else {
                log.info("Нет резолюций в архиве");
                return ResponseEntity.ok("Нет резолюций в архиве");
            }
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска архивных резолюций");
            return ResponseEntity.status(502).build();
        }
    }

    /**
     * Получение List резолюций без архивных
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/withoutArchived")
    private ResponseEntity<?> withoutArchived(){
        log.info("Получение всех резолюций, кроме архивных");
        try {
            List<ResolutionDto> resolutionList = resolutionService.findResolution(false);
            if (resolutionList != null){
                log.info("Список всех резолюций, кроме архивных получен");
                return ResponseEntity.ok(resolutionList);
            }
            else {
                log.info("Все резолюции в архиве");
                return ResponseEntity.ok("Все резолюции в архиве");
            }
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска всех резолюций, кроме архивных");
            return ResponseEntity.status(502).build();
        }
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
        ResolutionDto updatedResolutionDto = resolutionService.archiveResolution(id);
        if (updatedResolutionDto == null) {
            log.warn("Resolution with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedResolutionDto);
    }

    @PostMapping("/validate")
    @Operation(summary = "Проверяет корректность полей резолюции")
    public ResponseEntity<String> validateResolution(
            @Parameter(description = "Объект DTO резолюции", required = true)
            @RequestBody String resolutionDtoString) {
        log.info("Валидация резолюции");
        Map<String, String> map = resolutionService.validateResolution(resolutionDtoString);
        if (map.isEmpty()) {
            log.info("Валидация завершена");
            return ResponseEntity.ok("");
        }
        log.warn("Имеются ошибки");
        JSONObject jsonObject = new JSONObject(map);
        return ResponseEntity.badRequest().body(jsonObject.toString());
    }
}
