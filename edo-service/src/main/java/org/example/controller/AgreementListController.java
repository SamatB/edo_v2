package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.AgreementListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с сущностью AgreementList - Листа согласования.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/agreement-lists")
@Tag(name = "AgreementList")
public class AgreementListController {
    private final AgreementListService agreementListService;

    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     *
     * @param agreementListId идентификатор листа согласования
     * @return объект agreementListDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */
    @PutMapping("/send/{agreementListId}")
    @Operation(summary = "Отправляет лист согласования всем заинтересованным лицам")
    public ResponseEntity<?> sendAgreementList(
            @Parameter(description = "Идентификатор листа согласования", required = true)
            @PathVariable Long agreementListId) {
        try {
            log.info("Отправка листа согласования с id {} всем заинтересованным лицам", agreementListId);
            return ResponseEntity.ok().body(agreementListService.sendAgreementList(agreementListId));
        } catch (Exception e) {
            log.error("Ошибка отправки листа согласования с id {} всем заинтересованным лицам в БД", agreementListId);
            return ResponseEntity.badRequest().body(e);
        }
    }
}
