package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FilePoolDto;
import org.example.service.impl.FilePoolServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с сущностью FilePool.
 */
@RestController
@RequestMapping("/file-pool")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "FilePools")
public class FilePoolController {
    private final FilePoolServiceImpl filePoolService;

    /**
     * Сохраняет FilePool в базе данных.
     *
     * @param filePoolDto объект DTO FilePool
     * @return значение UUID сохраненного объекта FilePool
     */
    @PostMapping
    @Operation(summary = "Сохраняет FilePool в базе данных")
    public ResponseEntity<String> saveFilePool(
            @Parameter(description = "Объект DTO FilePool", required = true)
            @RequestBody FilePoolDto filePoolDto) {
        if (filePoolDto == null) {
            log.warn("Объект FilePoolDto не должен быть null");
            return ResponseEntity.badRequest().body("Объект FilePoolDto не должен быть null");
        }
        log.info("Сохранение описания FilePool в базу данных");
        FilePoolDto savedFilePoolDto = filePoolService.saveFilePool(filePoolDto);
        log.info("FilePool успешно сохранен");
        return ResponseEntity.ok(savedFilePoolDto.getStorageFileId().toString());
    }

    /**
     * Получает список UUID всех файлов, обращения которых старше 5 лет.
     * При ошибке получения возвращается ответ со статусом "Bad Request"
     *
     * @return Список UUID.
     */
    @GetMapping("/getolduuid")
    @Operation(summary = "Получение списка UUID всех файлов, старше 5 лет")
    public ResponseEntity<List<UUID>> getListOfOldRequestFile() {
        log.info("Получение списка UUID всех файлов, старше 5 лет");
        List<UUID> uuidList = filePoolService.getUUIDByCreationDateBeforeFiveYears();
        if (uuidList == null) {
            log.warn("Ошибка получения списка UUID - список пуст");
            return ResponseEntity.badRequest().build();
        }
        log.info("Список UUID, файлов старше 5 лет успешно получен");
        return ResponseEntity.ok(uuidList);
    }

    /**
     * Помечает в БД filePool, что файл удален из MinIO
     * При ошибке получения возвращается ответ со статусом "Bad Request"
     *
     * @param uuidList список UUID, файлы которых были удалены из MinIO
     * @return ответ в виде ResponseEntity со статусом 200.
     */
    @PostMapping("/markremoved")
    @Operation(summary = "Помечает filePool, что файл удален из Storage ")
    public ResponseEntity<?> markFilePoolRemoved(
            @Parameter(description = "Список UUID помечаемых filePools", required = true)
            @RequestBody List<UUID> uuidList) {
        log.info("Изменение статуса хранения файла в MinIO на 'удален'");
        if (uuidList == null) {
            log.warn("Ошибка - список файлов удаленных из MinIO пуст");
            return ResponseEntity.badRequest().build();
        }
        filePoolService.markThatTheFileHasBeenDeletedFromStorage(uuidList);
        log.info("Изменение статуса завершено успешно");
        return ResponseEntity.ok().build();
    }
}