package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.service.GetFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, который отвечает за обработку запросов на получение файлов.
 * Реализует эндпоинт /save/file/{uuid} для получения файла из сервера MinIO.
 */
@RestController
@RequestMapping("/get")
@Api(value = "Контроллер для получения файлов")
public class GetFileController {
    /**
     * Экземпляр GetFileStorageService, используемый для получения файлов.
     */
    private final GetFileStorageService getFileStorageService;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;

    /**
     * Конструктор контроллера GetFileController.
     *
     * @param getFileStorageService сервис для получения файлов
     */
    public GetFileController(GetFileStorageService getFileStorageService) {
        this.getFileStorageService = getFileStorageService;
        this.logger = LoggerFactory.getLogger(GetFileController.class);
    }

    /**
     * Метод для получения файла по-заданному UUID.
     *
     * @param uuid UUID файла
     * @return ответ с файлом или статусом "Not Found", если файл не найден
     */
    @ApiOperation(value = "Получение файла по UUID", notes = "Метод для получения файла по заданному UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл успешно получен"),
            @ApiResponse(code = 404, message = "Файл не найден")
    })
    @GetMapping("/file/{uuid}")
    public ResponseEntity<Resource> getFile(@PathVariable("uuid") String uuid) {
        logger.info("Выполняется получение файла по UUID: " + uuid);
        ResponseEntity<Resource> response = getFileStorageService.getFile(uuid);
        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Файл успешно получен по UUID: " + uuid);
        } else {
            logger.warn("Файл не найден по UUID: " + uuid);
        }
        return response;
    }
}