package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер, который отвечает за обработку запросов на сохранение и получение файлов.
 * Реализует эндпоинт /file/save для сохранения файла на сервер MinIO и
 * эндпоинт /file/get/{uuid} для получения файла из сервера MinIO и
 * эндпоинт /file/deleteoldfiles/{uuidList}.
 */
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/file")
@Api(value = "Контроллер для сохранения, удаления и получения файлов")
public class FileController {
    /**
     * Экземпляр FileStorageService, используемый для сохранения, удаления и получения файлов.
     */
    private final FileStorageService fileStorageService;

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
        log.info("Выполняется сохранение файла на сервер MinIO...");
        try {
            ResponseEntity<String> responseEntity = fileStorageService.saveFile(file);
            log.info("Файл успешно сохранен на сервер MinIO.");
            return responseEntity;
        } catch (Exception e) {
            log.error("Ошибка при сохранении файла на сервер MinIO.", e);
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
        log.info("Выполняется получение файла с сервера MinIO по UUID: " + uuid);
        ResponseEntity<Resource> response = fileStorageService.getFile(uuid);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Файл с сервера MinIO успешно получен по UUID: " + uuid);
        } else {
            log.warn("Файл на сервере MinIO не найден по UUID: " + uuid);
        }
        return response;
    }

    /**
     * Метод для удаления файлов по-заданному списку UUID.
     * Реализует эндпоинт /file/deleteoldfiles/{uuidList}.
     *
     * @param uuidList список UUID файлов
     * @return ответ с HTTP статусом 200 или
     * со статусом 500 при ошибке
     */
    @ApiOperation(value = "Удаление файлов по UUID", notes = "Метод для удаления файлов по заданным UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файлы успешно удалены"),
            @ApiResponse(code = 500, message = "Ошибка при сохранении файла. Возвращает HTTP статус 500.")
    })
    @DeleteMapping("/deleteoldfiles/{uuidList}")
    public ResponseEntity<HttpStatus> deleteOldFiles(@PathVariable("uuidList") List<UUID> uuidList) {
        log.info("Удаление файлов с сервера MinIO, чьи обращения старше 5 лет" + uuidList.toString());
        try {
            fileStorageService.deleteOldestThanFiveYearsFiles(uuidList);
            log.info("Файлы с сервера MinIO успешно удалены");
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            log.warn("Ошибка при удаление файлов с сервера MinIO");
            return ResponseEntity.status(500).build();
        }
    }
}