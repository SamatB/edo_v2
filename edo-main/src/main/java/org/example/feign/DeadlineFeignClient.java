package org.example.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.dto.DeadlineDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Клиент для для отправки запросов к edo-service, работа с сущностью Deadline.
 */
@FeignClient(name = "edo-service")
public interface DeadlineFeignClient {

    /**
     * Устанавливает или изменяет дату дедлайна, может быть добавлено описание причины переноса.
     */
    @PostMapping({"/deadline/setdeadline/{id}"})
    @Operation(summary = "Задает или изменяет дату дедлайна")
    DeadlineDto setOrUpdateDeadline(@PathVariable("id") Long resolutionId,
                                    @Parameter(description = "объект DTO дедлайна", required = true)
                                    @RequestBody DeadlineDto deadlineDto);
}
