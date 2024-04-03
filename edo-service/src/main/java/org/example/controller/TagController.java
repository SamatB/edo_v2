package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TagDto;
import org.example.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tag")
public class TagController {

    private final TagService tagService;

    /**
     * Сохраняет новую метку в базу данных.
     * При ошибке сохранения возвращается ответ со статусом "Bad Request"
     *
     * @return ответ с Dto объектом метки в виде ResponseEntity<TagDto>
     */
    @PostMapping
    @Operation(summary = "Сохраняет новую метку в базу данных")
    ResponseEntity<TagDto> saveTag(@RequestBody TagDto tagDto) {
        log.info("Сохранение метки");
        try {
            TagDto savedTag = tagService.saveTag(tagDto);
            log.info("Метка успешна сохранена. id метки " + savedTag.getId());
            return ResponseEntity.ok(savedTag);
        } catch (Exception e) {
            log.error("Ошибка сохранения метки: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
