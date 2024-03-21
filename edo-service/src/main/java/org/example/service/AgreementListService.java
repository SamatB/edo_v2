package org.example.service;

import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;

public interface AgreementListService {
    AgreementList sendAgreementList(Long id);
}
