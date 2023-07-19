/**
 * Класс для работы с классами, связанными с Appeal в сервисе edo-service.
 */
package org.example.feign;

import org.example.dto.AppealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "edo-service")
public interface AppealFeignClient {

    /**
     * Метод для сохранения обращения
     */
    @PostMapping("/appeal")
    AppealDto saveAppeal(@RequestBody AppealDto appeal);

    /**
     * Метод для получения обращения по id
     */
    @GetMapping("/appeal/{id}")
    AppealDto getAppeal(@PathVariable Long id);

    /**
     * Метод для архивации обращения по id
     */
    @PatchMapping("/appeal/{id}")
    AppealDto archiveAppeal(@PathVariable Long id);
}