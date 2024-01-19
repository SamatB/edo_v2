package org.example.controller;

import org.example.dto.AppealDto;
import org.example.feign.AppealFeignClient;
import org.example.utils.AppealStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса AppealController.
 */
public class AppealControllerTest {

    @Mock
    AppealFeignClient appealFeignClient;

    @InjectMocks
    AppealController appealController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода getAppeal.
     */
    @Test
    public void testGetAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.getAppeal(anyLong())).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.getAppeal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
        assertEquals(appealController.logCountOfRequestsToAppeal(), 1);

    }

    /**
     * Тест для метода getAppeal c несуществующим обращением.
     */
    @Test
    public void testGetAppealNotFound() {
        when(appealFeignClient.getAppeal(anyLong())).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.getAppeal(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода saveAppeal.
     */
    @Test
    public void testSaveAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.saveAppeal(any(AppealDto.class))).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.saveAppeal(appealDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода saveAppeal с нулевым обращением.
     */
    @Test
    public void testSaveAppealBadRequest() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.saveAppeal(any(AppealDto.class))).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.saveAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода archiveAppeal.
     */
    @Test
    public void testArchiveAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.archiveAppeal(anyLong())).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.archiveAppeal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода archiveAppeal с несуществующим обращением.
     */
    @Test
    public void testArchiveAppealNotFound() {
        when(appealFeignClient.archiveAppeal(anyLong())).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.archiveAppeal(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода registerAppeal.
     */
    @Test
    void testRegisterAppeal() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNumber("АБВ-12345");
        appealDto.setAppealStatus(AppealStatus.REGISTERED);
        appealDto.setStatusChanged(true);
        when(appealFeignClient.registerAppeal(anyLong())).thenReturn(appealDto);
        ResponseEntity<AppealDto> response = appealController.registerAppeal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isStatusChanged());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода registerAppeal с несуществующим обращением.
     */
    @Test
    public void testRegisterAppealNotFound() {
        ResponseEntity<AppealDto> response = appealController.archiveAppeal(Long.MAX_VALUE);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации зарегистрированного обращения.
     */
    @Test
    void testRegisterAppealRepeatedRegistration() {
        AppealDto appealDto = new AppealDto();
        appealDto.setAppealStatus(AppealStatus.REGISTERED);
        appealDto.setStatusChanged(false);
        when(appealFeignClient.registerAppeal(anyLong())).thenReturn(appealDto);
        ResponseEntity<AppealDto> response = appealController.registerAppeal(1L);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isStatusChanged());
        assertEquals(appealDto, response.getBody());
    }
}
