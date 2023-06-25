package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiParam;
import org.example.service.SaveFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер, который отвечает за обработку запросов на сохранение файлов в MinIO.
 * Реализует эндпоинт /save/file для сохранения файла на сервере MinIO.
 */
@RestController
@RequestMapping("/save")
@Api(value = "Контроллер для сохранения файлов в MinIO")
public class SaveFileController {
    /**
     * Экземпляр сервиса SaveFileStorageService, используемый для сохранения файлов в MinIO.
     */
    private final SaveFileStorageService saveFileStorageService;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;

    /**
     * Конструктор контроллера SaveFileController.
     *
     * @param saveFileStorageService сервис для сохранения файлов в MinIO
     */
    public SaveFileController(SaveFileStorageService saveFileStorageService) {
        this.saveFileStorageService = saveFileStorageService;
        this.logger = LoggerFactory.getLogger(SaveFileController.class);
    }

    /**
     * Метод для сохранения файла в MinIO.
     *
     * @param file загружаемый файл
     * @return ответ с UUID сохраненного файла в случае успешного сохранения,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    @ApiOperation(value = "Сохранение файла в MinIO", notes = "Метод для сохранения файла в MinIO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное сохранение файла. Возвращает UUID сохраненного файла."),
            @ApiResponse(code = 500, message = "Ошибка при сохранении файла. Возвращает HTTP статус 500.")
    })
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@ApiParam(value = "Загружаемый файл", required = true)
                                           @RequestPart("file") MultipartFile file) {
        try {
            logger.info("Выполняется сохранение файла в MinIO...");
            return saveFileStorageService.saveFile(file);
        } catch (Exception e) {
            logger.error("Ошибка при сохранении файла в MinIO.", e);
            return ResponseEntity.status(500).build();
        }
    }
}