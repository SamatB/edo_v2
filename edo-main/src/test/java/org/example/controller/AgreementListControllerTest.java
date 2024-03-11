package org.example.controller;

import org.example.dto.AgreementListDto;
import org.example.feign.AgreementListFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса AgreementListController.
 */
public class AgreementListControllerTest {
    @Mock
    private AgreementListFeignClient agreementListFeignClient;

    @InjectMocks
    private AgreementListController agreementListController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода sendAgreementList.
     */
    @Test
    public void testSendAgreementList() {
        AgreementListDto agreementListDto = new AgreementListDto();
        when(agreementListFeignClient.sendAgreementList(anyLong())).thenReturn(agreementListDto);

        ResponseEntity<AgreementListDto> response = agreementListController.sendAgreementList(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementListDto, response.getBody());
    }

    /**
     * Тест для метода sendAgreementList с несуществующим листом согласования.
     */
    @Test
    public void testSendAgreementListNotFound() {
        when(agreementListFeignClient.sendAgreementList(anyLong())).thenReturn(null);

        ResponseEntity<AgreementListDto> response = agreementListController.sendAgreementList(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
