package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReportDto;
import org.example.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param reportDto объект DTO отчета.
     * @return сохраненный объект resolutionDto со статусом 200 в случае успешного выполнения
     * и статусом 400 в случае неудачи.
     */
    @PostMapping()
    @Operation(summary = "Сохраняет отчет по резолюции в базе данных")
    public ResponseEntity<ReportDto> saveReport(
            @Parameter(description = "Объект отчета резолюции", required = true)
            @RequestBody ReportDto reportDto) {
        log.info("Сохранение отчета");
        try {
            log.info("Отчет сохранен в базе данных");
            return ResponseEntity.ok().body(reportService.saveReport(reportDto));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
