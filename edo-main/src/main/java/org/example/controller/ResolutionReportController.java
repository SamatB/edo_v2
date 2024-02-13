package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionReportDto;
import org.example.feign.ResolutionReportFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью Report.
 */
@RestController
@RequestMapping("/resolution-report")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ResolutionReport")
public class ResolutionReportController {

    private final ResolutionReportFeignClient resolutionReportFeignClient;

    /**
     * Сохраняет отчет по резолюции в базе данных.
     *
     * @param resolutionReportDto объект DTO отчета
     * @return сохраненный объект DTO отчета
     */
    @PostMapping
    @Operation(summary = "Сохраняет отчет по резолюции в базе данных")
    public ResponseEntity<ResolutionReportDto> saveReport(
            @Parameter(description = "Объект DTO отчета", required = true)
            @RequestBody ResolutionReportDto resolutionReportDto) {
        log.info("Сохранение отчета");
        try {
            return ResponseEntity.ok(resolutionReportFeignClient.saveResolutionReport(resolutionReportDto));
        } catch (Exception e) {
            log.error("Ошибка при сохранении отчета");
            return ResponseEntity.badRequest().build();
        }
    }
}
