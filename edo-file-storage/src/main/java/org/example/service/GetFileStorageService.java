package org.example.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * Сервис для получения файлов из хранилища.
 */
public interface GetFileStorageService {
    /**
     * Метод для получения файла по-заданному UUID.
     *
     * @param uuid UUID файла
     * @return ответ с файлом или статусом "Not Found", если файл не найден
     */
    ResponseEntity<Resource> getFile(String uuid);
}