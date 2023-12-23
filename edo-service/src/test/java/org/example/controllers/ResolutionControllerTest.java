package org.example.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ResolutionDto;
import org.example.service.ResolutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса ResolutionController.
 */

class ResolutionControllerTest {

    @Mock
    private ResolutionService resolutionService;

    @InjectMocks
    private ResolutionController resolutionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода saveResolution.
     */
    @Test
    void saveResolution_should_try() {
        ResolutionDto resolutionDto = new ResolutionDto();


        when(resolutionService.saveResolution(any(ResolutionDto.class))).thenReturn(resolutionDto);

        ResponseEntity<?> response = resolutionController.saveResolution(resolutionDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }


    /**
     * Тест для метода archiveResolution.
     */
    @Test
    void archiveResolution_shouldTry() {
        ResolutionDto resolutionDto = new ResolutionDto();

        when(resolutionService.archiveResolution(anyLong())).thenReturn(resolutionDto);

        ResponseEntity<?> response = resolutionController.archiveResolution(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }


    /**
     * Тест для метода archiveResolution с исключением.
     */
    @Test
    void archiveResolution_shouldExeption() {

        when(resolutionService.archiveResolution(anyLong())).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = resolutionController.archiveResolution(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    /**
     * Тест для метода getResolution.
     */
    @Test
    void getResolution_shouldTry() {
        ResolutionDto resolutionDto = new ResolutionDto();

        when(resolutionService.getResolution(anyLong())).thenReturn(resolutionDto);

        ResponseEntity<?> response = resolutionController.getResolution(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }


    /**
     * Тест для метода getResolution с исключением.
     */
    @Test
    void getResolution_shouldExeption() {


        when(resolutionService.getResolution(anyLong())).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = resolutionController.getResolution(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }


    /**
     * Тест для метода updateResolution.
     */
    @Test
    void updateResolution_shouldTry() {
        ResolutionDto resolutionDto = new ResolutionDto();

        when(resolutionService.updateResolution(anyLong(), any(ResolutionDto.class))).thenReturn(resolutionDto);

        ResponseEntity<?> response = resolutionController.updateResolution(1L, resolutionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());

    }


    /**
     * Тест для метода updateResolution с исключением.
     */
    @Test
    void updateResolution_shouldExeption() {

        when(resolutionService.updateResolution(anyLong(), any(ResolutionDto.class))).thenThrow(new EntityNotFoundException());

        ResponseEntity<?> response = resolutionController.updateResolution(1L, new ResolutionDto());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


    }
}