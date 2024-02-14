package org.example.feign;

import org.example.dto.ResolutionReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface ResolutionReportFeignClient {
    /**
     * Сохраняет отчет в базе данных.
     *
     * @param resolutionReportDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @PostMapping("/resolution-report")
    ResolutionReportDto saveResolutionReport(ResolutionReportDto resolutionReportDto);
}
