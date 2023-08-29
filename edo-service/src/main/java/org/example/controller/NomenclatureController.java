package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NomenclatureDto;
import org.example.repository.NomenclatureRepository;
import org.example.service.NomenclatureService;
import org.junit.jupiter.api.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * API для взаимодействия с сущностью Nomenclature
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/nomenclature")
@Tag("Nomenclature")
public class NomenclatureController {
    private final NomenclatureService nomenclatureService;

    /**
     * Эндпоинт сохранения сущности Nomenclature
     * @param nomenclatureDto - принимает на сохранение DTO Nomenclature
     * @return - возвращает DTO Nomenclature со статусом 200 в случае успешного сохранения, в случае ошибки 400-bad request с пустым телом ответа
     */
    @PostMapping
    @Operation(summary = "Сохранение сущности Nomenclature в БД")
    public ResponseEntity<?> saveNomenclature(@RequestBody NomenclatureDto nomenclatureDto) {
            if (nomenclatureDto==null) {
                return ResponseEntity.badRequest().build();
            }
        try {
            return ResponseEntity.ok().body(nomenclatureService.saveNomenclature(nomenclatureDto));
        } catch (Exception e) {
            log.info("Error when save entity with index {}", nomenclatureDto.getIndex());
            return ResponseEntity.badRequest().body("Saving nomenclature failed");
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
    public ResponseEntity<?> deleteNomenclature(@Parameter(name = "id"
            , required = true
            , example = "1") @PathVariable Long id) {
        try {
            nomenclatureService.deleteNomenclature(id);
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
    @Operation(summary = "Предоставляет выборку данных сущности Nomenclature по заданным критериям.")
    public ResponseEntity<?> getPaginatedNomenclature(
            @Parameter(name = "offset"
                    ,required = false
                    , example = "1") @RequestParam(name = "offset", defaultValue = "0", required = false)@Min(0) int offset,
            @Parameter(name = "size"
                    ,required = false
                    , example = "7") @RequestParam(name = "size", defaultValue = "5", required = false)@Max(15) int size) {
        try {
            return ResponseEntity.ok().body(nomenclatureService.getPaginatedNomenclature(offset, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            nomenclatureService.ArchiveNomenclature(id, archive);
    }

    /**
     *
     * @param isarchived - параметр задающий логику получения сущностей. true - достаем архивные, false - неархивные.
     * @return возвращает List сущностей со статусом 200 если все ОК или 400 bad request.
     */
    @GetMapping("/getarchived")
    @Operation(summary = "Получаем архивную/неархивную номенклатуру.")
    public ResponseEntity<?> getArchivedOrNoArchivedNomenclature(
            @Parameter(name = "isarchived"
                    , required = true
                    , description = "Возвращает архивированную/неархивированную версию сущности Nomenclature."
                    , example = "false") @RequestParam(name = "isarchived", defaultValue = "false") boolean isarchived) {
        try {
            return ResponseEntity.ok().body(nomenclatureService.getArchivedOrNoArchivedNomenclature(isarchived));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
