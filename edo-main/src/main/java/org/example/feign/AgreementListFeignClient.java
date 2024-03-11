package org.example.feign;

import org.example.dto.AgreementListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Клиент для работы с сервисом edo-service.
 */
@FeignClient(name = "edo-service")
public interface AgreementListFeignClient {
    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     *
     * @param id идентификатор листа согласования
     * @return объект agreementListDto со статусом 200 в случае успешного выполнения,
     * и статусом 400 в случае не удачи.
     */
    @PutMapping("/agreement-lists/send/{id}")
    AgreementListDto sendAgreementList(@PathVariable Long id);
}
