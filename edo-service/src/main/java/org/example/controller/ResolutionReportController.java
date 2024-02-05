package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionReportDto;
import org.example.service.ResolutionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/resolution-report")
public class ResolutionReportController {

    private final ResolutionReportService resolutionReportService;

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionReportDto объект DTO отчета.
     * @return сохраненный объект resolutionDto со статусом 200 в случае успешного выполнения
     * и статусом 400 в случае неудачи.
     */
    @PostMapping()
    @Operation(summary = "Сохраняет отчет по резолюции в базе данных")
    public ResponseEntity<ResolutionReportDto> saveResolutionReport(
            @Parameter(description = "Объект отчета резолюции", required = true)
            @RequestBody ResolutionReportDto resolutionReportDto) {
        log.info("Сохранение отчета");
        try {
            log.info("Отчет сохранен в базе данных");
            return ResponseEntity.ok().body(resolutionReportService.saveResolutionReport(resolutionReportDto));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
