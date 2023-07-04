package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер, который отвечает за обработку запросов на сохранение и получение файлов.
 * Реализует эндпоинт /file/save для сохранения файла на сервер MinIO и
 * эндпоинт /file/get/{uuid} для получения файла из сервера MinIO.
 */
@RestController
@RequestMapping("/file")
@Api(value = "Контроллер для получения файлов")
public class FileController {
    /**
     * Экземпляр FileStorageService, используемый для сохранения и получения файлов.
     */
    private final FileStorageService fileStorageService;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;

    /**
     * Конструктор контроллера FileController.
     *
     * @param fileStorageService сервис для получения файлов
     */
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
        this.logger = LoggerFactory.getLogger(FileController.class);
    }

    /**
     * Метод для сохранения файла в MinIO.
     * Реализует эндпоинт /save/file.
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
    @PostMapping("/save")
    public ResponseEntity<String> saveFile(@ApiParam(value = "Загружаемый файл", required = true)
                                           @RequestPart("file") MultipartFile file) {
        logger.info("Выполняется сохранение файла на сервер MinIO...");
        try {
            ResponseEntity<String> responseEntity = fileStorageService.saveFile(file);
            logger.info("Файл успешно сохранен на сервер MinIO.");
            return responseEntity;
        } catch (Exception e) {
            logger.error("Ошибка при сохранении файла на сервер MinIO.", e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Метод для получения файла по-заданному UUID.
     * Реализует эндпоинт /get/file/{uuid}.
     *
     * @param uuid UUID файла
     * @return ответ с файлом или статусом "Not Found", если файл не найден
     */
    @ApiOperation(value = "Получение файла по UUID", notes = "Метод для получения файла по заданному UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл успешно получен"),
            @ApiResponse(code = 404, message = "Файл не найден")
    })
    @GetMapping("/get/{uuid}")
    public ResponseEntity<Resource> getFile(@PathVariable("uuid") String uuid) {
        logger.info("Выполняется получение файла с сервера MinIO по UUID: " + uuid);
        ResponseEntity<Resource> response = fileStorageService.getFile(uuid);
        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Файл с сервера MinIO успешно получен по UUID: " + uuid);
        } else {
            logger.warn("Файл на сервере MinIO не найден по UUID: " + uuid);
        }
        return response;
    }
}