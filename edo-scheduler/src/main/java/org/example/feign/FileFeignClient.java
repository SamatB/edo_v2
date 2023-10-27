package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

/**
 * Клиент для работы с сервисом edo-file-storage.
 */
@FeignClient(name = "edo-file-storage")
public interface FileFeignClient {

    /**
     * Метод для удаления старых файлов
     *
     * @param uuidList список UUID файлов
     * @return ответ с HTTP статусом 200 или
     * со статусом 500 при ошибке
     */
    @DeleteMapping(value = "/file/deleteoldfiles/{uuidList}")
    ResponseEntity<HttpStatus> deleteOldFiles(@PathVariable("uuidList") List<UUID> uuidList);
}
