package org.example.feign;

import org.example.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface ReportFeignClient {
    /**
     * Сохраняет отчет в базе данных.
     *
     * @param reportDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @PostMapping("/report")
    ReportDto saveReport(ReportDto reportDto);
}
