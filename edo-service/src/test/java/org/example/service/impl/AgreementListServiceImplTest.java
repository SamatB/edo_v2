package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
 * Работает только с enable_lazy_load_no_trans: true в настройках hibernate
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
    @DisplayName("Should return agreement list and get agreement list by id from database")
    void sendAgreementList_returnsAgreementList() {
        AgreementList result = agreementListService.sendAgreementList(1L);
        AgreementList agreementList = agreementListRepository.findById(1L).get();
//        AgreementListDto agreementListDto = agreementListMapper.entityToDto(agreementList);

        assertNotNull(result);
        assertEquals(agreementList, result);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when agreement list not found by id")
    void sendAgreementList_ThrowsEntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> agreementListService.sendAgreementList(0L));
    }
}