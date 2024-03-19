package org.example.controller;

import jakarta.persistence.EntityExistsException;
import org.example.dto.AppealDto;
import org.example.service.impl.AppealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса AppealController.
 */
class AppealControllerTest {
    @Mock
    private AppealServiceImpl appealService;

    @InjectMocks
    AppealController appealController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Тест для метода reserveNumberForAppeal.
     */
    @Test
    public void testReserveNumberForAppeal_Ok() {
        AppealDto appealDto = new AppealDto();
        AppealDto expected = new AppealDto();
        expected.setReservedNumber("АБВ-12345");

        when(appealService.reserveNumberForAppeal(any(AppealDto.class))).thenReturn(expected);
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(appealService, times(1)).reserveNumberForAppeal(appealDto);
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым обращением.
     */
    @Test
    public void testReserveNumberForAppeal_NullAppeal_BadRequest() {
        when(appealService.reserveNumberForAppeal(eq(null))).thenThrow(new IllegalArgumentException());

        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым nomenclature.
     */
    @Test
    public void testReserveNumberForAppeal_NullNomenclature_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNomenclature(null);
        appealDto.setAnnotation("Тестовое обращение");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new IllegalArgumentException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже существующим номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingNumber_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNumber("АБВ-12345");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new EntityExistsException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже зарезервированным номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingReservedNumber_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setReservedNumber("АБВ-12345");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new EntityExistsException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}