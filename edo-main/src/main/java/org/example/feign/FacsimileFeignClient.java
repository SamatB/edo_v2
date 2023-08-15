package org.example.feign;

import org.example.dto.FacsimileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "edo-service", path = "/facsimile")
public interface FacsimileFeignClient {

    /**
     * Метод для сохранения факсимиле
     */
    @PostMapping()
    FacsimileDto saveFacsimile(@RequestBody FacsimileDto facsimile);

    /**
     * Метод для удаления факсимиле
     */
    @DeleteMapping("/{id}")
    void deleteFacsimile(@PathVariable Long id);

    /**
     * Метод, меняющий статус архивации/разархивации факсимиле
     */
    @PostMapping("/toggle-archived-status/{id}")
    FacsimileDto toggleArchivedStatus(@PathVariable Long id);

    /**
     * Метод получения списка факсимиле с указанным статусом архивации
     */
    @GetMapping("/")
    List<FacsimileDto> getFacsimilesByArchivedStatus(@RequestParam(name = "isArchived") boolean isArchived);

    /**
     * Метод получения списка факсимиле с пагинацией
     */
    @GetMapping("/paged")
    List<FacsimileDto> getPaginatedFacsimiles(@RequestParam int page, @RequestParam int pageSize);
}
