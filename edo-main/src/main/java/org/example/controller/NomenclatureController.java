package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NomenclatureDto;
import org.example.feign.NomenclatureFeignClient;
import org.junit.jupiter.api.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * контроллер для работы с сущностью Nomenclature
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/nomenclature")
@Tag("Nomenclature")
public class NomenclatureController {
    private final NomenclatureFeignClient nomenclatureFeignClient;

    /**
     * Эндпоинт сохранения сущности Nomenclature
     * @param nomenclatureDto - принимает на сохранение DTO Nomenclature
     * @return - возвращает DTO Nomenclature со статусом 200 в случае успешного сохранения, в случае ошибки 400-bad request с пустым телом ответа
     */
    @PostMapping
    @Operation(summary = "Сохранение Nomenclature")
    private ResponseEntity<?> saveNomenclature(@RequestBody NomenclatureDto nomenclatureDto) {
        try {
            return ResponseEntity.ok().body(nomenclatureFeignClient.saveNomenclature(nomenclatureDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    /**
     * Эндпоинт удаления сущности Nomenclature
     * @param id - принимает id удаляемой сущности Nomenclature
     * @return - возвращает статус 204 в случае успешного удаления,
     * в случае ошибки 400-bad request с пустым телом ответа
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление записи в таблице 'nomenclature' по id. ")
    private ResponseEntity<?> deleteNomenclature(@Parameter(name = "id"
            , required = true
            , example = "1") @PathVariable Long id) {
        log.info("Parameter {} getted.", id);
        try {
            nomenclatureFeignClient.deleteNomenclature(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    /**
     *
     * @param offset - задает смещение пагинации(страницу пагинации),начиная с 0
     * @param size - количество предоставляемых сущностей в выборке
     * @return List<NomenclatureDto> со статусом 200 в случае успешного выполнения
     * в случае неудачи возвращает 204 с пустым телом ответа
     */
    @GetMapping("/pagination")
    @Operation(summary = "Получение выборки сущностей Nomenclature.")
    private ResponseEntity<?> getPaginatedNomenclature(
            @Parameter(name = "offset"
                    ,required = false
                    , example = "1") @RequestParam(name = "offset", defaultValue = "0", required = false)@Min(0) int offset,
            @Parameter(name = "size"
                    ,required = false
                    , example = "7") @RequestParam(name = "size", defaultValue = "5", required = false)@Max(15) int size) {
        try {
            return ResponseEntity.ok().body(nomenclatureFeignClient.getPaginatedNomenclature(offset, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    /**
     *
     * @param isArchived маркер в какой статус переводим сущность. при архивации (true) присваиваем дату и время архивации,
     *                 при разархивации обнуляем поле архивации.
     * @param id - id сущности над которой проводится операция.
     */
    @GetMapping("/getarchived")
    @Operation(summary = "Получаем архивную/неархивную номенклатуру.")
    private ResponseEntity<?> getArchivedOrNoArchivedNomenclature(
            @Parameter(name = "isArchived"
                    , required = true
                    , description = "Возвращает архивированную/неархивированную версию сущности Nomenclature."
                    , example = "false") @RequestParam(name = "size", defaultValue = "false") boolean isArchived) {
        try {
            return ResponseEntity.ok().body(nomenclatureFeignClient.getArchivedOrNoArchivedNomenclature(isArchived));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     *
     * @param archive маркер в какой статус переводим сущность. при архивации (true) присваиваем дату и время архивации,
     *                 при разархивации обнуляем поле архивации.
     * @param id - id сущности над которой проводится операция.
     */
    @PostMapping("/archivate")
    @Operation(summary = "Переключение статуса архивации номенклатуры.")
    public void switchArchivationStatus(@RequestParam(name = "archive") boolean archive,
                                        @RequestParam(name = "id") Long id) {
        log.info("запрос в контроллере");
        nomenclatureFeignClient.switchArchivationStatus(archive, id);
    }
}