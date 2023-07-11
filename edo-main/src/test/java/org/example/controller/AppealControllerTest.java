package org.example.controller;

import org.example.dto.AppealDto;
import org.example.feign.AppealFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * Тест для метода saveAppeal с несуществующим обращением.
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
}
