package org.example.service;

import org.example.dto.DeadlineDto;
import org.example.entity.Appeal;
import org.example.entity.Deadline;
import org.example.mapper.DeadlineMapper;
import org.example.repository.DeadlineRepository;
import org.example.service.impl.DeadlineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Класс для юнит-тестирования контроллера FilePoolService
 */
@DisplayName("Тестирование сервиса DeadlineService")
public class DeadlineServiceTest {

    @Mock
    private DeadlineRepository deadlineRepository;

    @Mock
    private DeadlineMapper deadlineMapper;

    @InjectMocks
    private DeadlineServiceImpl deadlineService;
    @Mock
    private ResolutionService resolutionService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверка корректной работы метода по установке или обновлению дедлайна
     */
    @Test
    void testSetOrUpdateDeadline() {
        // Создаем тестовые данные
        DeadlineDto deadlineDto = new DeadlineDto();
        Deadline deadlineMock = new Deadline();
        Long resolutionId = 1L;

        // Мокаем репозиторий и маппер
        when(deadlineMapper.dtoToEntity(deadlineDto)).thenReturn(deadlineMock);
        when(deadlineMapper.entityToDto(deadlineMock)).thenReturn(deadlineDto);
        when(deadlineRepository.findByResolutionId(resolutionId)).thenReturn(deadlineMock);

        DeadlineDto result = deadlineService.setOrUpdateDeadline(1L, deadlineDto);

        assertEquals(deadlineDto, result);
    }

    /**
     *
     */
    @Test
    public void testGetAllByResolution_Question_Appeal_Id() {

        List<DeadlineDto> deadlineDtoList = mock(List.class);
        Appeal appeal = mock(Appeal.class);

        when(deadlineMapper.entityListToDtoList(
                deadlineRepository.getDeadlinesByAppeal(appeal.getId(), 0)))
                .thenReturn(deadlineDtoList);


        List<DeadlineDto> deadlineDtos = deadlineService.getDeadlinesByAppeal(2L, 0);

        assertEquals(deadlineDtoList, deadlineDtos);
    }

}