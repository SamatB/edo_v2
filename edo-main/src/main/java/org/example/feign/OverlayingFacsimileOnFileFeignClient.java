package org.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Клиент для работы с сервисом edo-file-storage по наложению факсимиле на файл.
 */
@FeignClient(name = "edo-file-storage")
public interface OverlayingFacsimileOnFileFeignClient {

    @PostMapping(value = "/overlay-facsimile-on-file")
    ResponseEntity<String> signFile(@RequestParam String fileUUID, @RequestParam String facsimileUUID);
}
