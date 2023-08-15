package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FacsimileDto;
import org.example.feign.FacsimileFeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с сущностью Facsimile.
 */
@RestController
@RequestMapping("/facsimile")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Facsimile")
public class FacsimileController {

    private final FacsimileFeignClient facsimileFeignClient;

    /**
     * Сохраняет новое факсимиле в базу данных.
     * При ошибке сохранения возвращается ответ со статусом "Bad Request"
     * Метод выполняет сохранение факсимиле с помощью FacsimileFeignClient.
     *
     * @param facsimileDto сохраненный объект DTO факсимиле.
     * @return ответ с Dto объектом факсимиле в виде ResponseEntity<FacsimileDto>
     */
    @PostMapping
    @Operation(summary = "Сохраняет новое факсимиле в базу данных")
    public ResponseEntity<FacsimileDto> saveFacsimile(
            @Parameter(description = "Объект DTO факсимиле", required = true)
            @RequestBody FacsimileDto facsimileDto) {
        log.info("Сохранение нового факсимиле");
        try {
            FacsimileDto savedFacsimile = facsimileFeignClient.saveFacsimile(facsimileDto);
            log.info("Факсимиле успешно сохранено");
            return ResponseEntity.ok(savedFacsimile);
        } catch (Exception e) {
            log.warn("Ошибка сохранения факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет факсимиле из базы данных.
     * При ошибке удаления возвращается ответ со статусом "Bad Request"
     * Метод выполняет удаление факсимиле с помощью FacsimileFeignClient.
     *
     * @param id идентификатор факсимиле, который следует удалить.
     * @return ответ об успешном удалении факсимиле со статусом 204
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет факсимиле из базы данных")
    public ResponseEntity<?> deleteFacsimile(@PathVariable Long id) {
        log.info("Удаление факсимиле c id = {}", id);
        try {
            facsimileFeignClient.deleteFacsimile(id);
            log.info("Факсимиле с id = {} успешно удален", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.warn("Ошибка удаления факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Изменяет статус архивации факсимиле.
     * При ошибке удаления возвращается ответ со статусом "Bad Request"
     * Метод выполняет изменение статуса архивации с помощью FacsimileFeignClient.
     *
     * @param id идентификатор факсимиле, статус архивации которого нужно изменить.
     * @return ответ с Dto объектом факсимиле в виде ResponseEntity<FacsimileDto>
     */
    @PostMapping("/toggle-archived-status/{id}")
    @Operation(summary = "Меняет статус архивации/разархивации факсимиле")
    public ResponseEntity<FacsimileDto> toggleArchivedStatus(@PathVariable Long id) {
        log.info("Изменение статуса архивации факсимиле");
        try {
            FacsimileDto newStatusFacsimile = facsimileFeignClient.toggleArchivedStatus(id);
            log.info("Статус факсимиле с id = {} изменен на {}", id, newStatusFacsimile.isArchived() ? "архивирован" : "разархивирован");
            return ResponseEntity.ok(newStatusFacsimile);
        } catch (Exception e) {
            log.warn("Ошибка изменения статуса архивации факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает все факсимиле по статусу архивации/разархивации.
     * При ошибке получения возвращается ответ со статусом "Bad Request"
     * Метод выполняет запрос с помощью FacsimileFeignClient.
     *
     * @param isArchived статус архивации факсимиле, по которому нужно сделать выгрузку.
     * @return ответ со списком Dto объектов факсимиле в виде ResponseEntity<List<FacsimileDto>>
     */
    @GetMapping()
    @Operation(summary = "Получение всех факсимиле по статусу архивации/разархивации")
    public ResponseEntity<List<FacsimileDto>> getFacsimilesByArchivedStatus(@RequestParam(name = "isArchived") boolean isArchived) {
        log.info("Получение всех факсимиле со статусом: {}", (isArchived ? "архивирован" : "разархивирован"));
        try {
            List<FacsimileDto> facsimilesByArchivedStatus = facsimileFeignClient.getFacsimilesByArchivedStatus(isArchived);
            log.info("Список всех факсимиле со статусом {} получен", (isArchived ? "архивирован" : "разархивирован"));
            return ResponseEntity.ok(facsimilesByArchivedStatus);
        } catch (Exception e) {
            log.warn("Ошибка получения списка факсимиле с пагинацией: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает список факсимиле с пэйджингом.
     * При ошибке получения возвращается ответ со статусом "Bad Request"
     * Метод осуществляет получение списка факсимиле с применением пэйджинга.
     *
     * @param page     Номер страницы для выгрузки (начиная с 0).
     * @param pageSize Количество элементов на странице.
     * @return Список факсимиле на заданной странице с указанным размером страницы.
     */
    @GetMapping("/paged")
    @Operation(summary = "Получение всех факсимиле по заданному номеру и размеру страницы")
    public ResponseEntity<List<FacsimileDto>> getPaginatedFacsimiles(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        log.info("Получение списка факсимиле с применением пэйджинга, страница {}, размер страницы {}", page, pageSize);
        try {
            List<FacsimileDto> paginatedFacsimiles = facsimileFeignClient.getPaginatedFacsimiles(page, pageSize);
            log.info("Список факсимиле с применением пэйджинга успешно получен");
            return ResponseEntity.ok(paginatedFacsimiles);
        } catch (Exception e) {
            log.warn("Ошибка получения списка факсимиле с пагинацией: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
