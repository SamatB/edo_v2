//package org.example.controllers;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.dto.ResolutionDto;
//import org.example.service.ResolutionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Контроллеры для работы с сущностью Resolution.
// */
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/resolutions")
//@Tag(name = "Resolution")
//public class ResolutionController2 {
//
//    private final ResolutionService resolutionService;
//
//    /**
//     * Сохраняет резолюцию в базе данных.
//     *
//     * @param resolutionDto объект DTO резолюции
//     * @return сохраненный объект resolutionDto со статусом 200 в случае успешного выполнения
//     * и статусом 400 в случае не удачи.
//     */
//
//    @PostMapping()
//    @Operation(summary = "Сохраняет новую резолюцию в базе данных")
//    ResponseEntity<?> saveResolution(
//            @Parameter(description = "Объект DTO резолюции", required = true)
//            ResolutionDto resolutionDto) {
//        try {
//            log.info("Сохранение новой резолюции");
//            return ResponseEntity.ok().body(resolutionService.saveResolution(resolutionDto));
//        } catch (Exception e) {
//            log.error("Ошибка сохранения резолюции в БД");
//            return ResponseEntity.badRequest().body(e);
//        }
//
//    }
//
//
//    /**
//     * Переносит резолюцию в архив.
//     *
//     * @param id идентификатор резолюции
//     * @return обновленный объект resolutionDto со статусом 200 в случае успешного выполнения,
//     * и статусом 400 в случае не удачи.
//     */
//
////    @PutMapping("/{id}/archive")
////    @Operation(summary = "Переносит резолюцию в архив")
////    ResponseEntity<?> archiveResolution(
////            @Parameter(description = "Идентификатор резолюции", required = true)
////            @PathVariable Long id) {
////
////        try {
////            log.info("Архивация резолюции в БД");
////            return ResponseEntity.ok().body(resolutionService.archiveResolution(id));
////        } catch (Exception e) {
////            log.error("Ошибка архивации резолюции в БД");
////            return ResponseEntity.badRequest().body(e);
////        }
////    }
//
//
//    /**
//     * Возвращает резолюцию по идентификатору.
//     *
//     * @param id идентификатор резолюции
//     * @return объект resolutionDto со статусом 200 в случае успешного выполнения,
//     * и статусом 400 в случае не удачи.
//     */
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Возвращает резолюцию по идентификатору")
//    ResponseEntity<?> getResolution(
//            @Parameter(description = "Идентификатор резолюции", required = true)
//            @PathVariable Long id) {
//        try {
//            log.info("Поиск резолюции по идентификатору");
//            return ResponseEntity.ok().body(resolutionService.getResolution(id));
//        } catch (Exception e) {
//            log.error("Ошибка поиска резолюции  по идентификатору в БД");
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//
//    /**
//     * Обновляет данные резолюции.
//     *
//     * @param id            идентификатор резолюции
//     * @param resolutionDto объект DTO с новыми данными резолюции
//     * @return обновленный объект resolutionDto со статусом 200 в случае успешного выполнения,
//     * и статусом 400 в случае не удачи.
//     */
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Обновляет данные резолюции")
//    ResponseEntity<?> updateResolution(
//            @Parameter(description = "Идентификатор резолюции", required = true)
//            @PathVariable Long id,
//            @Parameter(description = "Объект DTO с новыми данными резолюции", required = true)
//            @RequestBody ResolutionDto resolutionDto) {
//
//        try {
//            log.info("Обновление резолюции в БД");
//            return ResponseEntity.ok().body(resolutionService.updateResolution(id, resolutionDto));
//        } catch (Exception e) {
//            log.error("Ошибка обновления резолюции в БД");
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//
//}
