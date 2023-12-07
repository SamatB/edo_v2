package org.example.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionMapper;
import org.example.service.ResolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/resolutions")
public class ResolutionController {

    private final ResolutionService resolutionService;
    private final ResolutionMapper resolutionMapper;


    /**
     * Получение List всех резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/all")
    private ResponseEntity<Collection<ResolutionDto>> getAll(){
        log.info("Получение всех резолюций");
        try {
            List<Resolution> resolutionList = resolutionService.findResolution(null);
            log.info("Список резолюций получен");
            return ResponseEntity.ok(resolutionMapper.entityListToDtoList(resolutionList));
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска всех резолюций");
            return ResponseEntity.status(502).build();
        }
    }

    /**
     * Получение List архивных резолюций
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/archived")
    private ResponseEntity<?> getArchived(){
        log.info("Получение архивных резолюций");
        try {
            List<Resolution> resolutionList = resolutionService.findResolution(true);
            if (resolutionList != null){
                log.info("Список архивных резолюций получен");
                return ResponseEntity.ok(resolutionList);
            }
            else {
                log.info("Нет резолюций в архиве");
                return ResponseEntity.ok("Нет резолюций в архиве");
            }
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска архивных резолюций");
            return ResponseEntity.status(502).build();
        }
    }

    /**
     * Получение List резолюций без архивных
     *
     * @return возвращает List сущностей ResolutionDto со статусом 200 если все ОК или 502 Bad Gateway.
     */
    @GetMapping("/withoutArchived")
    private ResponseEntity<?> withoutArchived(){
        log.info("Получение всех резолюций, кроме архивных");
        try {
            List<Resolution> resolutionList = resolutionService.findResolution(false);
            if (resolutionList != null){
                log.info("Список всех резолюций, кроме архивных получен");
                return ResponseEntity.ok(resolutionList);
            }
            else {
                log.info("Все резолюции в архиве");
                return ResponseEntity.ok("Все резолюции в архиве");
            }
        }
        catch (Exception e){
            log.error("Возникла ошибка поиска всех резолюций, кроме архивных");
            return ResponseEntity.status(502).build();
        }
    }
}
