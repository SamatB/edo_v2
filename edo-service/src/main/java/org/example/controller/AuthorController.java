package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthorDto;
import org.example.service.AuthorService;
import org.example.utils.Employment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * API для взаимодействия с сущностью Author
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/authors")
@Tag(name = "Author")
public class AuthorController {
    private final AuthorService authorService;

    /**
     * @param search - строка для поиска
     * @return List<AuthorDto> со статусом 200 в случае успешного выполнения
     */
    @GetMapping("/")
    @Operation(summary = "Возвращает список авторов по первым буквам ФИО")
    public ResponseEntity<List<AuthorDto>> getAuthorsByFirstLetters(
            @Parameter(description = "Строка для поиска", required = true)
            @RequestParam String search) {
        log.info("Начат поиск авторов по строке в service: " + search);
        try {
            List<AuthorDto> authorsList;
            authorsList = authorService.getAuthorsByFirstLetters(search);
            log.info("Список авторов в service получен");
            return ResponseEntity.ok(authorsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
