package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FilePoolDto;
import org.example.service.impl.FilePoolServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(filePoolDto==null) {
            log.warn("Объект FilePoolDto не должен быть null");
            return ResponseEntity.badRequest().body("Объект FilePoolDto не должен быть null");
        }
        log.info("Сохранение описания FilePool в базу данных");
        FilePoolDto savedFilePoolDto = filePoolService.saveFilePool(filePoolDto);
        log.info("FilePool успешно сохранен");
        return ResponseEntity.ok(savedFilePoolDto.getStorageFileId().toString());
    }
}
