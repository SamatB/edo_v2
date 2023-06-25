package org.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для сохранения файлов.
 */
public interface SaveFileStorageService {
    /**
     * Метод для сохранения файла в MinIO.
     *
     * @param file загружаемый файл
     * @return ответ с UUID сохраненного файла в случае успешного сохранения,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    ResponseEntity<String> saveFile(MultipartFile file);
}