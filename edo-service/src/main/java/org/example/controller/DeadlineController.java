package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DeadlineDto;
import org.example.service.DeadlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с сущностью Deadline.
 */
@RestController
@RequestMapping("/deadline")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "deadlines")
public class DeadlineController {
    private final DeadlineService deadlineService;

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
        log.info("Изменение даты дедлайна....");
        try {
            DeadlineDto savedDeadline = deadlineService.setOrUpdateDeadline(resolutionId, deadlineDto);
            log.info("Установка даты дедлайна завершена успешно");
            return ResponseEntity.ok(savedDeadline);
        } catch (Exception e) {
            log.error("Установка даты дедлайна завершена с ошибкой: " + e);
            return ResponseEntity.status(503).build();
        }
    }

    /**
     * Получение дедлайнов всех резолюциий по идентификатору обращения.
     *
     * @param appealId - идентификатор обращения.
     * @return ответ со списком дедлайнов всех резолюций, найденных по идентификатору обращения.
     */
    @GetMapping("/getAllOnAppeal/{id}")
    @Operation(summary = "Получает дедлайны всех резолюций по идентификатору обращения",
            description = "Обращение должно существовать")
    public ResponseEntity<List<DeadlineDto>> getResolutionDeadlines(@PathVariable("id") Long appealId) {
        log.info("Получение всех дедлайнов");
        try {
            List<DeadlineDto> deadlineDtoList = deadlineService.getResolutionDeadlines(appealId, null);
            log.info("Дедлайны резолюций получены");
            return ResponseEntity.ok(deadlineDtoList);
        } catch (Exception e) {
            log.warn("Получение дедлайнов завершено с ошибкой" + e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получение дедлайнов архивных резолюциий по идентификатору обращения.
     *
     * @param appealId - идентификатор обращения.
     * @return ответ со списком дедлайнов архивных резолюций, найденных по идентификатору обращения.
     */
    @GetMapping("/getArchivedOnAppeal/{id}")
    @Operation(summary = "Получает дедлайны архивных резолюций по идентификатору обращения",
            description = "Обращение должно существовать")
    public ResponseEntity<List<DeadlineDto>> getArchivedResolutionDeadlines(@PathVariable("id") Long appealId) {
        log.info("Получение дедлайнов архивных резолюций");
        try {
            List<DeadlineDto> deadlineDtoList = deadlineService.getResolutionDeadlines(appealId, true);
            if (deadlineDtoList != null) {
                log.info("Дедлайны архивных резолюций получены");
                return ResponseEntity.ok(deadlineDtoList);
            } else {
                log.info("Нет резолюций в архиве");
                return ResponseEntity.status(204).build();
            }
        } catch (Exception e) {
            log.warn("Получение дедлайнов завершено с ошибкой" + e);
            return ResponseEntity.status(502).build();
        }
    }

    /**
     * Получение дедлайнов резолюциий, находящихся не в архиве, по идентификатору обращения.
     *
     * @param appealId - идентификатор обращения.
     * @return ответ со списком дедлайнов всех найденных по идентификатору обращения резолюций, находящихся не в архиве.
     */
    @GetMapping("/getNotArchivedOnAppeal/{id}")
    @Operation(summary = "Получает дедлайны архивных резолюций по идентификатору обращения",
            description = "Обращение должно существовать")
    public ResponseEntity<List<DeadlineDto>> getNotArchivedResolutionDeadlines(@PathVariable("id") Long appealId) {
        log.info("Получение дедлайнов резолюций, находящихся не в архиве.");
        try {
            List<DeadlineDto> deadlineDtoList = deadlineService.getResolutionDeadlines(appealId, false);
            if (deadlineDtoList != null) {
                log.info("Дедлайны резолюций, находящихся не в архиве, получены");
                return ResponseEntity.ok(deadlineDtoList);
            } else {
                log.info("Все резолюции в архиве");
                return ResponseEntity.status(204).build();
            }
        } catch (Exception e) {
            log.warn("Получение дедлайнов завершено с ошибкой" + e);
            return ResponseEntity.status(502).build();
        }
    }

}
