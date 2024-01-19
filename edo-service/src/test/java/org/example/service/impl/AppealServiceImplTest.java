package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.utils.AppealStatus;
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

    /**
     * Тест для метода registerAppeal.
     */
    @Test
    public void testRegisterAppeal() {
        AppealDto appealDto = new AppealDto();
        Appeal appealMock = mock(Appeal.class);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appealMock));
        when(appealRepository.save(appealMock)).thenReturn(appealMock);
        when(appealMapper.entityToDto(appealMock)).thenReturn(appealDto);

        AppealDto test = appealService.registerAppeal(1L);
        assertEquals(appealDto, test);
        assertTrue(test.isStatusChanged());
        verify(appealMock).setRegistrationDate(any(ZonedDateTime.class));
    }

    /**
     * Тест для метода registerAppeal с несуществующим обращением.
     */
    @Test
    public void testRegisterAppealNotFound() {
        assertThrows(EntityNotFoundException.class, () -> appealService.registerAppeal(Long.MAX_VALUE));
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации зарегистрированного обращения
     */
    @Test
    public void testRegisterAppealRepeatedRegistration() {
        AppealDto appealDto = new AppealDto();
        Appeal appeal = new Appeal();
        appeal.setAppealStatus(AppealStatus.REGISTERED);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));
        when(appealMapper.entityToDto(appeal)).thenReturn(appealDto);
        assertFalse(appealService.registerAppeal(1L).isStatusChanged());
    }
}
