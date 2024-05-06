package org.example.service;

import org.example.utils.FilePoolType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для сохранения файлов в хранилище и получения файлов из хранилища.
 */
public interface FileStorageService {
    /**
     * Метод для сохранения файла в MinIO.
     *
     * @param file загружаемый файл
     * @return ответ с UUID сохраненного файла в случае успешного сохранения,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    ResponseEntity<String> saveFile(MultipartFile file, FilePoolType fileType);

    /**
     * Метод для создания бакета (контейнера) в MinIO, если его не существует.
     * Он проверяет, существует ли бакет с указанным именем в MinIO, и если нет, то создает его.
     *
     * @param bucketName имя создаваемого бакета
     */
    void createBuketInMinioIfNotExist(String bucketName);

    /**
     * Метод для получения файла по-заданному UUID.
     *
     * @param uuid UUID файла
     * @return ответ с файлом или статусом "Not Found", если файл не найден
     */
    ResponseEntity<Resource> getFile(String uuid);

    /**
     * Метод для удаления файлов из хранилища MinIO по-заданным UUID(список UUID).
     *
     * @param uuidList список uuid, которые нужно удалить
     * @return ResponseEntity.HTTPStatus.ok  в случае успешного удаления,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    ResponseEntity<UUID> deleteOldestThanFiveYearsFiles(@RequestBody List<UUID> uuidList);
}