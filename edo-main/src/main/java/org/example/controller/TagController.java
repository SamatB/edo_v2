package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TagDto;
import org.example.feign.TagFeignClient;
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

    private final TagFeignClient tagFeignClient;


    /**
     * Сохраняет новую метку в базу данных.
     * Если метка равна null, то возвращается ответ со статусом "Bad Request"
     * Метод выполняет сохранение метки с помощью TagFeignClient.
     *
     * @param tagDto сохраненный объект DTO метки.
     * @return ответ с Dto объектом метки в виде ResponseEntity<TagDto>
     */
    @PostMapping
    @Operation(summary = "Сохраняет новую метку в базу данных")
    public ResponseEntity<TagDto> saveTag(
            @Parameter(description = "Объект DTO метка", required = true)
            @RequestBody TagDto tagDto) {
        log.info("Сохранение новой метки");
        TagDto savedTag = tagFeignClient.saveTag(tagDto);
        if (savedTag == null) {
            log.warn("Ошибка сохранения метки: метка не должна быть null");
            return ResponseEntity.badRequest().build();
        }
        log.info("Метка с названием: " + tagDto.getName() + " успешно сохранена");
        return ResponseEntity.ok(savedTag);
    }
}
