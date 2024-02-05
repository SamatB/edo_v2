package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReportDto;
import org.example.feign.ReportFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью Report.
 */
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Report")
public class ReportController {

    private final ReportFeignClient reportFeignClient;

    /**
     * Сохраняет отчет по резолюции в базе данных.
     *
     * @param reportDto объект DTO отчета
     * @return сохраненный объект DTO отчета
     */
    @PostMapping
    @Operation(summary = "Сохраняет отчет по резолюции в базе данных")
    public ResponseEntity<ReportDto> saveReport(
            @Parameter(description = "Объект DTO отчета", required = true)
            @RequestBody ReportDto reportDto) {
        log.info("Сохранение отчета");
        try {
            log.info("Отчет сохранен в базе данных");
            return ResponseEntity.ok(reportFeignClient.saveReport(reportDto));
        } catch (Exception e) {
            log.error("Ошибка при сохранении отчета");
            return ResponseEntity.badRequest().build();
        }
    }
}
