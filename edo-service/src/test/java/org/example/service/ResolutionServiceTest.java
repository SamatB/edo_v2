package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса ResolutionService.
 */
public class ResolutionServiceTest {

    @Mock
    private ResolutionRepository resolutionRepository;

    @Mock
    private ResolutionMapper resolutionMapper;

    @InjectMocks
    private ResolutionService resolutionService;

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
        Resolution resolutionMock = mock(Resolution.class);
        when(resolutionMapper.dtoToEntity(resolutionDto)).thenReturn(resolutionMock);
        when(resolutionRepository.save(resolutionMock)).thenReturn(resolutionMock);
        when(resolutionMapper.entityToDto(resolutionMock)).thenReturn(resolutionDto);

        ResolutionDto result = resolutionService.saveResolution(resolutionDto);
        assertEquals(resolutionDto, result);
        verify(resolutionMock).setCreationDate(any(ZonedDateTime.class));
        verify(resolutionMock).setLastActionDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода archiveResolution.
     */
    @Test
    public void testArchiveResolution() {
        Long id = 1L;
        Resolution resolutionMock = mock(Resolution.class);
        when(resolutionRepository.findById(id)).thenReturn(Optional.of(resolutionMock));
        when(resolutionRepository.save(resolutionMock)).thenReturn(resolutionMock);
        ResolutionDto resolutionDto = new ResolutionDto();
        when(resolutionMapper.entityToDto(resolutionMock)).thenReturn(resolutionDto);

        ResolutionDto result = resolutionService.archiveResolution(id);
        assertEquals(resolutionDto, result);
        verify(resolutionMock).setArchivedDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода archiveResolution с несуществующей резолюцией.
     */
    @Test
    public void testArchiveResolutionNotFound() {
        Long id = 1L;
        when(resolutionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resolutionService.archiveResolution(id));
    }

    /**
     * Тест для метода getResolution.
     */
    @Test
    public void testGetResolution() {
        Long id = 1L;
        Resolution resolution = new Resolution();
        when(resolutionRepository.findById(id)).thenReturn(Optional.of(resolution));
        ResolutionDto resolutionDto = new ResolutionDto();
        when(resolutionMapper.entityToDto(resolution)).thenReturn(resolutionDto);

        ResolutionDto result = resolutionService.getResolution(id);
        assertEquals(resolutionDto, result);
    }

    /**
     * Тест для метода getResolution с несуществующей резолюцией.
     */
    @Test
    public void testGetResolutionNotFound() {
        Long id = 1L;
        when(resolutionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resolutionService.getResolution(id));
    }

    /**
     * Тест для метода updateResolution.
     */
    @Test
    public void testUpdateResolution() {
        Long id = 1L;
        Resolution resolutionMock = mock(Resolution.class);
        when(resolutionRepository.findById(id)).thenReturn(Optional.of(resolutionMock));
        when(resolutionRepository.save(resolutionMock)).thenReturn(resolutionMock);
        ResolutionDto resolutionDto = new ResolutionDto();
        when(resolutionMapper.entityToDto(resolutionMock)).thenReturn(resolutionDto);

        ResolutionDto result = resolutionService.updateResolution(id, resolutionDto);
        assertEquals(resolutionDto, result);
        verify(resolutionMapper).updateEntity(resolutionDto, resolutionMock);
        verify(resolutionMock).setLastActionDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода updateResolution с несуществующей резолюцией.
     */
    @Test
    public void testUpdateResolutionNotFound() {
        Long id = 1L;
        when(resolutionRepository.findById(id)).thenReturn(Optional.empty());
        ResolutionDto resolutionDto = new ResolutionDto();

        assertThrows(EntityNotFoundException.class, () -> resolutionService.updateResolution(id, resolutionDto));
    }
}