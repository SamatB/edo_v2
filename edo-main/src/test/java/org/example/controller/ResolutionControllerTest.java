package org.example.controller;

import org.example.dto.ResolutionDto;
import org.example.feign.EdoServiceClient;
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
 * Тесты для класса ResolutionController.
 */
public class ResolutionControllerTest {

    @Mock
    private EdoServiceClient edoServiceClient;

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
    public void testSaveResolution() {
        ResolutionDto resolutionDto = new ResolutionDto();
        when(edoServiceClient.saveResolution(any(ResolutionDto.class))).thenReturn(resolutionDto);

        ResponseEntity<ResolutionDto> response = resolutionController.saveResolution(resolutionDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }

    /**
     * Тест для метода archiveResolution.
     */
    @Test
    public void testArchiveResolution() {
        ResolutionDto resolutionDto = new ResolutionDto();
        when(edoServiceClient.archiveResolution(anyLong())).thenReturn(resolutionDto);

        ResponseEntity<ResolutionDto> response = resolutionController.archiveResolution(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }

    /**
     * Тест для метода archiveResolution с несуществующей резолюцией.
     */
    @Test
    public void testArchiveResolutionNotFound() {
        when(edoServiceClient.archiveResolution(anyLong())).thenReturn(null);

        ResponseEntity<ResolutionDto> response = resolutionController.archiveResolution(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода getResolution.
     */
    @Test
    public void testGetResolution() {
        ResolutionDto resolutionDto = new ResolutionDto();
        when(edoServiceClient.getResolution(anyLong())).thenReturn(resolutionDto);

        ResponseEntity<ResolutionDto> response = resolutionController.getResolution(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }

    /**
     * Тест для метода getResolution с несуществующей резолюцией.
     */
    @Test
    public void testGetResolutionNotFound() {
        when(edoServiceClient.getResolution(anyLong())).thenReturn(null);

        ResponseEntity<ResolutionDto> response = resolutionController.getResolution(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода updateResolution.
     */
    @Test
    public void testUpdateResolution() {
        ResolutionDto resolutionDto = new ResolutionDto();
        when(edoServiceClient.updateResolution(anyLong(), any(ResolutionDto.class))).thenReturn(resolutionDto);

        ResponseEntity<ResolutionDto> response = resolutionController.updateResolution(1L, resolutionDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resolutionDto, response.getBody());
    }

    /**
     * Тест для метода updateResolution с несуществующей резолюцией.
     */
    @Test
    public void testUpdateResolutionNotFound() {
        when(edoServiceClient.updateResolution(anyLong(), any(ResolutionDto.class))).thenReturn(null);

        ResponseEntity<ResolutionDto> response = resolutionController.updateResolution(1L, new ResolutionDto());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
