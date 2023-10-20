package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface FilePoolFeignClient {

    /**
     * Метод для получения списка UUID, старых обращений
     *
     * @return список UUID файлов, обращения которых созданы более 5 лет назад
     */
    @GetMapping("/file-pool/getolduuid")
    ResponseEntity<List<UUID>> getListOfOldRequestFile();
}
