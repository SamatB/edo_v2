package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AgreementListDto;
import org.example.feign.AgreementListFeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью AgreementList - Листа согласования.
 */
@RestController
@RequestMapping("/agreement-lists")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "AgreementList")
public class AgreementListController {
    private final AgreementListFeignClient agreementListFeignClient;

    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     *
     * @param id идентификатор листа согласования
     * @return объект agreementListDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */
    @PutMapping("/send/{id}")
    @Operation(summary = "Отправляет лист согласования всем заинтересованным лицам")
    public ResponseEntity<AgreementListDto> sendAgreementList(
            @Parameter(description = "Идентификатор листа согласования", required = true)
            @PathVariable Long id) {
        log.info("Отправка листа согласования с идентификатором {} всем заинтересованным лицам", id);
        AgreementListDto agreementListDto = agreementListFeignClient.sendAgreementList(id);
        if (agreementListDto == null) {
            log.warn("Лист согласования с идентификатором {} не найден", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(agreementListDto);
    }
}
