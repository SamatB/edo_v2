package org.example.feign;

import org.example.dto.AuthorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "edo-service", path = "/authors")
public interface AuthorFeignClient {
    /**
     * Возвращает список авторов по строке ФИО
     *
     * @param search строка поиска
     * @return список объектов DTO автора
     */
    @GetMapping("/")
    List<AuthorDto> getAuthorsByFirstLetters(@RequestParam String search);
}
