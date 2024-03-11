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

import java.util.List;

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
     * Получение дедлайнов всех резолюций по идентификатору Обращения
     *
     * @param appealId - идентификатор обращения
     */
    @GetMapping("/getDeadlinesByAppeal/{id}")
    @Operation(summary = "Получает дедлайн резолюции по идентификатору обращения",
            description = "Обращение должно существовать")
    public ResponseEntity<List<DeadlineDto>> getDeadlinesByAppeal(@PathVariable("id") Long appealId,
                                                                    @Parameter(description = "0 - все резолюции, 1 - архивные, 2 - не в архиве")
                                                                    @RequestParam Integer archived) {


        log.info("Получение дедлайнов...");
        if (archived == 1) {
            log.info("Получение дедлайнов резолюций, находящихся в архиве...");
            try {
                List<DeadlineDto> deadlineDtoList = deadlineFeignClient.getDeadlinesByAppeal(appealId, archived);
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
        } else if (archived == 2) {
            log.info("Получение дедлайнов резолюций, находящихся не в архиве...");
            try {
                List<DeadlineDto> deadlineDtoList = deadlineFeignClient.getDeadlinesByAppeal(appealId, archived);
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
        } else if (archived == 0) {
            log.info("Получение дедлайнов всех резолюций...");
            try {
                List<DeadlineDto> deadlineDtoList = deadlineFeignClient.getDeadlinesByAppeal(appealId, archived);
                log.info("Дедлайны резолюций получены");
                return ResponseEntity.ok(deadlineDtoList);
            } catch (Exception e) {
                log.warn("Получение дедлайнов завершено с ошибкой" + e);
                return ResponseEntity.notFound().build();
            }
        } else
            log.warn("Получение дедлайнов завершено с ошибкой");
        return ResponseEntity.notFound().build();
    }

}
