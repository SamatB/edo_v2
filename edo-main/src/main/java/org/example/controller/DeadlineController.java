package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DeadlineDto;
import org.example.feign.DeadlineFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Контроллер для работы установки и изменения дедлайна.
 */
@RestController
@RequestMapping("/deadline")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "deadlines")
public class DeadlineController {
    private final DeadlineFeignClient deadlineFeignClient;

    /**
     * Устанавливает или изменяет дату дедлайна, может быть добавлено описание причины переноса.
     *
     * @param resolutionId - идентификатор резолюции, для которой выставляется дедлайн,
     * @param deadlineDto  - ДТО объект дедлайна,
     * @return ответ с Dto объектом дедлайн в виде ResponseEntity<DeadlineDto>
     */
    @PostMapping({"/setdeadline/{id}"})
    @Operation(summary = "Задает или изменяет дату дедлайна")
    public ResponseEntity<DeadlineDto> setOrUpdateDeadline(@PathVariable("id") Long resolutionId,
                                                           @Parameter(description = "объект DTO дедлайна", required = true)
                                                           @RequestBody DeadlineDto deadlineDto) {
        log.info("Изменение даты дедлайна...");
        try {
            DeadlineDto savedDeadline = deadlineFeignClient.setOrUpdateDeadline(resolutionId, deadlineDto);
            log.info("Установка даты дедлайна завершена успешно");
            return ResponseEntity.ok(savedDeadline);
        } catch (Exception e) {
            log.error("Установка даты дедлайна завершена с ошибкой " + e);
            return ResponseEntity.status(503).build();
        }
    }

    /**
     * Метод возвращает список объектов DeadlineDto  по идентификатору обращения с учетом нахождения резолюций в архиве.
     * @param appealId - идентификатор обращения
     * @param archived - флаг, указывающий на архивацию "null - все резолюции, true - архивные, false - не в архиве"
     * @return ответ со списком объектов DeadlineDto
     */
    @GetMapping("/getDeadlinesByAppeal/{id}")
    @Operation(summary = "Получает дедлайн резолюции по идентификатору обращения",
            description = "Обращение должно существовать")
    public ResponseEntity<Collection<DeadlineDto>> getDeadlinesByAppeal(@PathVariable("id") Long appealId,
                                                                        @Parameter(description = "null - все резолюции, true - архивные, false - не в архиве")
                                                                        @RequestParam(required = false) Boolean archived) {
        try {
            Collection<DeadlineDto> deadlineDtoList = deadlineFeignClient.getDeadlinesByAppeal(appealId, archived);
            if (archived == null) {
                log.info("Получены дедлайны резолюций");
                return ResponseEntity.ok(deadlineDtoList);
            } else {
                log.info("Получены дедлайны резолюций, с учетом нахождения резолюций в архиве");
                return deadlineDtoList != null && !deadlineDtoList.isEmpty()
                        ?  ResponseEntity.ok(deadlineDtoList)
                        :  ResponseEntity.status(204).build();
            }
        } catch (Exception e) {
            log.warn("Получение дедлайнов завершено с ошибкой" + e);
            return ResponseEntity.notFound().build();
        }
    }
}
