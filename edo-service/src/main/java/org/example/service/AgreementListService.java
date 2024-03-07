package org.example.service;

import org.example.dto.AgreementListDto;

public interface AgreementListService {
    AgreementListDto saveAgreementList(AgreementListDto agreementListDto);
    AgreementListDto getAgreementList(Long id);
    AgreementListDto updateAgreementList(Long id, AgreementListDto agreementListDto);
    AgreementListDto sendAgreementList(Long id);
}
