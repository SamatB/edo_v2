package org.example.feign;

import org.example.dto.FilePoolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface FilePoolFeignClient {

    /**
     * Метод для сохранения файла
     *
     * @param filePoolDto FilePoolDto, который нужно сохранить в БД
     * @return значение UUID сохраненного FilePool
     */
    @PostMapping("/file-pool")
    ResponseEntity<String> saveFile(FilePoolDto filePoolDto);
}
