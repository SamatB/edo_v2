package org.example.service;

import org.example.dto.FilePoolDto;
import org.example.entity.FilePool;
import org.example.mapper.FilePoolMapper;
import org.example.repository.FilePoolRepository;
import org.example.service.impl.FilePoolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Класс для юнит-тестирования контроллера FilePoolService
 */
@DisplayName("Тестирование сервиса FilePoolService")
class FilePoolServiceTest {

    @Mock
    private FilePoolRepository filePoolRepository;

    @Mock
    private FilePoolMapper filePoolMapper;

    @InjectMocks
    private FilePoolServiceImpl filePoolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Проверка корректного сохранения FilePoolDto
     */
    @Test
    void saveFilePool() {
        FilePoolDto filePoolDto = new FilePoolDto();
        FilePool filePoolMock = new FilePool();
        when(filePoolMapper.dtoToEntity(filePoolDto)).thenReturn(filePoolMock);
        when(filePoolRepository.save(filePoolMock)).thenReturn(filePoolMock);
        when(filePoolMapper.entityToDto(filePoolMock)).thenReturn(filePoolDto);

        FilePoolDto test = filePoolService.saveFilePool(filePoolDto);
        assertEquals(filePoolDto, test);
    }
}