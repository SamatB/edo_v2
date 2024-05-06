package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Клиент для работы с сервисом edo-file-storage.
 */
@FeignClient(name = "edo-file-storage")
public interface FileFeignClient {

    /**
     * Метод для сохранения файла
     *
     * @param file файл, котрый нужно сохранить в БД
     * @return ResponseEntity со значением UUID сохраненного файла
     */
    @PostMapping(value = "/file/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> saveFile(@RequestPart("file") MultipartFile file);

}
