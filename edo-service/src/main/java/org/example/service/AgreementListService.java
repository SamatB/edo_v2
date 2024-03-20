package org.example.service;

import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;

public interface AgreementListService {
    AgreementListDto saveAgreementList(AgreementListDto agreementListDto);
    AgreementListDto getAgreementList(Long id);
    AgreementListDto updateAgreementList(Long id, AgreementListDto agreementListDto);
    AgreementList sendAgreementList(Long id);
}
