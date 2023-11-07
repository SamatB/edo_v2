package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.dto.AuthorDto;
import org.example.feign.AppealFeignClient;
import org.example.feign.AuthorFeignClient;
import org.example.utils.Employment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Контроллер для работы с сущностью Author
 */
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Author")
public class AuthorController {
    private final AuthorFeignClient authorFeignClient;

    /**
     * Метод поиска авторов по первым буквам ФИО
     *
     * @param search строка для поиска
     * @return список авторов ResponseEntity<List<AuthorDto>>
     */
    @GetMapping()
    @Operation(summary = "Возвращает список авторов по первым буквам ФИО")
    public ResponseEntity<List<AuthorDto>> getAuthorsByFirstLetters(
            @Parameter(description = "Строка для поиска", required = true)
            @RequestParam String search) {
        log.info("Начат поиск авторов по строке в main: " + search);
        if (search.length() < 3) {
            log.info("Минимальное количество символов для поиска: 3");
            return ResponseEntity.badRequest().build();
        }
        List<AuthorDto> authorsList = authorFeignClient.getAuthorsByFirstLetters(search);
        log.info("Список авторов в main получен");
        try {
            return ResponseEntity.ok(authorsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
