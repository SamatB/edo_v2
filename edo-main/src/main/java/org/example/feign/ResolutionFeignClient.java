package org.example.feign;

import org.example.dto.ResolutionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface ResolutionFeignClient {

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @PostMapping("/resolutions")
    ResolutionDto saveResolution(ResolutionDto resolutionDto);

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект DTO резолюции
     */
    @PutMapping("/resolutions/{id}/archive")
    ResolutionDto archiveResolution(@PathVariable Long id);

    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект DTO резолюции
     */
    @GetMapping("/resolutions/{id}")
    ResolutionDto getResolution(@PathVariable Long id);

    /**
     * Обновляет данные резолюции.
     *
     * @param id идентификатор резолюции
     * @param resolutionDto объект DTO с новыми данными резолюции
     * @return обновленный объект DTO резолюции
     */
    @PutMapping("/resolutions/{id}")
    ResolutionDto updateResolution(@PathVariable Long id, @RequestBody ResolutionDto resolutionDto);

    /**
     * Получение резолюций с архивным статусом.
     *
     * @param archived идентификатор архивного статуса резолюции
     * @return список резолюций с выбранным архивным статусом
     */
    @GetMapping("/resolutions/find")
    List<ResolutionDto> findResolutions(@RequestParam(name = "archiveStatus") Boolean archived);

    /**
     *
     * @param resolutionDto объект DTO с резолюцией
     * @return json, содержащий, имена и описания некорректно заполненных полей резолюции
     */
    @PostMapping("/resolutions/validate")
    String validateResolution(@RequestBody ResolutionDto resolutionDto);
}
