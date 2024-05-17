package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FacsimileDto;
import org.example.service.FacsimileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с сущностью Facsimile.
 */
@RestController
@RequestMapping("/facsimile")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Facsimile")
public class FacsimileController {

    private final FacsimileService facsimileService;

    /**
     * Сохраняет новое факсимиле в базу данных.
     * При ошибке сохранения возвращается ответ со статусом "Bad Request"
     *
     * @param facsimile сохраненный объект DTO факсимиле.
     * @return ответ с Dto объектом факсимиле в виде ResponseEntity<FacsimileDto>
     */
    @PostMapping
    @Operation(summary = "Сохраняет новое факсимиле в базу данных")
    ResponseEntity<FacsimileDto> saveFacsimile(@RequestBody FacsimileDto facsimile) {
        log.info("Сохранение факсимиле");
        try {
            FacsimileDto savedFacsimile = facsimileService.saveFacsimile(facsimile);
            log.info("Факсимиле успешно сохранен");
            return ResponseEntity.ok(savedFacsimile);
        } catch (Exception e) {
            log.error("Ошибка сохранения факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет факсимиле из базы данных.
     * При ошибке удаления возвращается ответ со статусом "Bad Request"
     *
     * @param id идентификатор факсимиле, который следует удалить.
     * @return ответ об успешном удалении факсимиле со статусом 204
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет факсимиле из базы данных")
    public ResponseEntity<?> deleteFacsimile(@PathVariable Long id) {
        log.info("Удаление факсимиле c id = {}", id);
        try {
            facsimileService.deleteFacsimile(id);
            log.info("Факсимиле с id = {} успешно удален", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Ошибка удаления факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получение UUID факсимиле файла из БД по id авторизованного Employee.
     */
    @GetMapping("/uuid/{id}")
    @Operation(summary = "Получает UUID факсимиле-излбражения (файла) из базы данных")
    public UUID getFacsimile(@PathVariable Long id) {
        return facsimileService.getFacsimileUUIDByUserID(id);
    }

    /**
     * Изменяет статус архивации факсимиле.
     * При ошибке удаления возвращается ответ со статусом "Bad Request"
     *
     * @param id идентификатор факсимиле, статус архивации которого нужно изменить.
     * @return ответ в виде ResponseEntity со статусом 200
     */
    @PostMapping("/toggle-archived-status/{id}")
    @Operation(summary = "Меняет статус архивации/разархивации факсимиле")
    public ResponseEntity<?> toggleArchivedStatus(@PathVariable Long id) {
        log.info("Изменение статуса архивации факсимиле");
        try {
            facsimileService.toggleArchivedStatus(id);
            log.info("Статус факсимиле с id = {} изменен", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка изменения статуса архивации факсимиле: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает все факсимиле по статусу архивации/разархивации.
     * При ошибке получения возвращается ответ со статусом "Bad Request"
     *
     * @param isArchived статус архивации факсимиле, по которому нужно сделать выгрузку.
     * @return ответ со списком Dto объектов факсимиле в виде ResponseEntity<List<FacsimileDto>>
     */
    @GetMapping("/")
    @Operation(summary = "Получение всех факсимиле по статусу архивации/разархивации")
    public ResponseEntity<List<FacsimileDto>> getFacsimilesByArchivedStatus(@RequestParam(name = "isArchived") boolean isArchived) {
        log.info("Получение всех факсимиле со статусом: {}", (isArchived ? "архивирован" : "разархивирован"));
        try {
            List<FacsimileDto> facsimilesByArchivedStatus = facsimileService.getFacsimilesByArchivedStatus(isArchived);
            log.info("Список всех факсимиле со статусом {} получен", (isArchived ? "архивирован" : "разархивирован"));
            return ResponseEntity.ok(facsimilesByArchivedStatus);
        } catch (Exception e) {
            log.error("Ошибка получения списка факсимиле по статусу архивации: " + e.getMessage());
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
        try{
            List<FacsimileDto> paginatedFacsimiles = facsimileService.getPaginatedFacsimiles(page, pageSize);
            log.info("Список факсимиле с применением пэйджинга успешно получен");
            return ResponseEntity.ok(paginatedFacsimiles);
        } catch (Exception e) {
            log.error("Ошибка получения списка факсимиле с пагинацией: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
