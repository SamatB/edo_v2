package org.example.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    ResponseEntity<String> saveFile(MultipartFile file);

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
}