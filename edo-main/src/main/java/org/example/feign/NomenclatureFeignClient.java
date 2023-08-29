package org.example.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dto.NomenclatureDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FeignClient(value = "edo-service", path = "/nomenclature")
public interface NomenclatureFeignClient {
        @PostMapping
        ResponseEntity<?> saveNomenclature(NomenclatureDto nomenclatureDto);

        @DeleteMapping("/{id}")
        ResponseEntity<?> deleteNomenclature(@PathVariable Long id);


        @GetMapping("/pagination")
        ResponseEntity<?> getPaginatedNomenclature(@RequestParam int offset,@RequestParam int size);

        @GetMapping("/getarchived")
        ResponseEntity<?> getArchivedOrNoArchivedNomenclature(@RequestParam boolean isArchived);
        @PostMapping("/archivate")
        void switchArchivationStatus(@RequestParam(name = "archive") boolean archive, @RequestParam(name = "id") Long id);

}
