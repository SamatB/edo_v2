package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;
import org.example.mapper.AgreementListMapper;
import org.example.repository.AgreementListRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса AgreementListServiceImpl.
 */

@Slf4j
@SpringBootTest
class AgreementListServiceImplTest {
    @Autowired
    private AgreementListRepository agreementListRepository;
    @Autowired
    private AgreementListMapper agreementListMapper;
    @Autowired
    private AgreementListServiceImpl agreementListService;

    @Test
    @DisplayName("Should return agreement list Dto and get agreement list by id from database")
    void sendAgreementList_returnsAgreementListDto() {
        AgreementList agreementList = agreementListRepository.findById(1L).orElseGet(AgreementList::new);
        AgreementListDto agreementListDto = agreementListMapper.entityToDto(agreementList);

        AgreementListDto result = agreementListService.sendAgreementList(1L);

        assertNotNull(result);
        assertEquals(agreementListDto.getAppeal().getId(), result.getAppeal().getId());
        assertEquals(agreementListDto.getComment(), result.getComment());
        assertEquals(agreementListDto.getInitiator(), result.getInitiator());
        assertEquals(agreementListDto.getCreationDate(), result.getCreationDate());
        assertEquals(agreementListDto.getSignatory().size(), result.getSignatory().size());
        assertEquals(agreementListDto.getCoordinating().size(), result.getCoordinating().size());
        assertNotEquals(agreementListDto.getSentApprovalDate(), result.getSentApprovalDate());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when agreement list not found by id")
    void sendAgreementList_ThrowsEntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> agreementListService.sendAgreementList(0L));
    }
}