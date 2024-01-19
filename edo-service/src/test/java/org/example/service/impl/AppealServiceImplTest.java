package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class AppealServiceImplTest {

    @Mock
    private AppealRepository appealRepository;

    @Mock
    private AppealMapper appealMapper;

    @InjectMocks
    private AppealServiceImpl appealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerAppeal() {

        // Тест 1
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appealMock));
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        AppealDto test = appealService.registerAppeal(1L);
        assertEquals(appealDto, test);
        verify(appealMock).setRegistrationDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода archiveAppeal с несуществующим обращением.
     */
    @Test
    public void registerAppealNotFound() {
        Appeal appeal = new Appeal();
        when(appealRepository.getReferenceById(1L)).thenReturn(appeal);

        assertThrows(EntityNotFoundException.class, () -> appealService.registerAppeal(2L));
    }
//
//    @Test
//    void registerAppealTest3() {
//    }
}