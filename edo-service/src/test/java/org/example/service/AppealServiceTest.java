/**
 * Тесты для класса AppealService.
 */

package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.entity.Nomenclature;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.service.impl.AppealServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AppealServiceTest {

    @Mock
    private AppealRepository appealRepository;

    @Mock
    private AppealMapper appealMapper;

    @InjectMocks
    private AppealServiceImpl appealService;

    @Mock
    private NomenclatureService nomenclatureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода getAppeal.
     */
    @Test
    public void testGetAppeal() {
        Appeal appeal = new Appeal();
        AppealDto appealDto = new AppealDto();
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));
        when(appealMapper.entityToDto(appeal)).thenReturn(appealDto);

        AppealDto test = appealService.getAppeal(1L);
        assertEquals(appealDto, test);
    }

    /**
     * Тест для метода getAppeal с несуществующим обращением.
     */
    @Test
    public void testGetAppealNotFound() {
        when(appealRepository.getReferenceById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> appealService.getAppeal(2L));
    }

    /**
     * Тест для метода saveAppeal.
     */
    @Test
    public void testSaveAppeal() {
        AppealDto appealDto = new AppealDto();
        Nomenclature nomenclatureMock = mock((Nomenclature.class));
        Appeal appealMock = mock(Appeal.class);
        when(appealMapper.dtoToEntity(appealDto)).thenReturn(appealMock);
        appealMock.setNumber(nomenclatureService.generateNumberForAppeal(nomenclatureMock));
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        AppealDto test = appealService.saveAppeal(appealDto);
        assertEquals(appealDto, test);
    }

    /**
     * Тест для метода archiveAppeal.
     */
    @Test
    public void testArchiveAppeal() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appealMock));
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        AppealDto test = appealService.archiveAppeal(1L);
        assertEquals(appealDto, test);
        verify(appealMock).setArchivedDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода archiveAppeal с несуществующим обращением.
     */
    @Test
    public void testArchiveAppealNotFound() {
        Appeal appeal = new Appeal();
        when(appealRepository.getReferenceById(1L)).thenReturn(appeal);

        assertThrows(EntityNotFoundException.class, () -> appealService.archiveAppeal(2L));
    }
}
