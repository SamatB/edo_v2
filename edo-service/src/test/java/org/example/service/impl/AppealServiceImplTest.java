package org.example.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.entity.Appeal;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.utils.AppealStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
     * Тест для метода registerAppeal - успешная попытка регистрации обращения.
     */
    @Test
    public void testRegisterAppeal() {
        Appeal appeal = mock(Appeal.class);
        appeal.setAppealStatus(AppealStatus.REGISTERED);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));

        appealService.registerAppeal(1L);

        verify(appeal, times(2)).setAppealStatus(AppealStatus.REGISTERED);
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации обращения с несуществующим id.
     */
    @Test
    public void testRegisterAppealNotFound() {
        assertThrows(EntityNotFoundException.class, () -> appealService.registerAppeal(Long.MAX_VALUE));
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации зарегистрированного обращения.
     */
    @Test
    public void testRegisterAppealRepeatedRegistration() {
        Appeal appeal = new Appeal();
        appeal.setAppealStatus(AppealStatus.REGISTERED);
        when(appealRepository.findById(anyLong())).thenReturn(Optional.of(appeal));

        assertThrows(EntityExistsException.class, () -> appealService.registerAppeal(1L));
    }
}
