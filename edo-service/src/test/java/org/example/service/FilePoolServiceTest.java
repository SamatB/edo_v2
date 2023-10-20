package org.example.service;

import org.example.dto.FilePoolDto;
import org.example.entity.Appeal;
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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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


    /**
     * Проверка корректной работы метода по поиску старых файлов, обращения которых зарегистрированы более 5 лет назад
     */
    @Test
    void getUUIDByCreationDateBeforeFiveYears() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        FilePool filePool1 = new FilePool();
        filePool1.setStorageFileId(uuid1);

        FilePool filePool2 = new FilePool();
        filePool2.setStorageFileId(uuid2);

        FilePool filePool3 = new FilePool();
        filePool3.setStorageFileId(uuid3);

        Appeal appeal1 = new Appeal();
        appeal1.setCreationDate(ZonedDateTime.now().minusYears(6));
        filePool1.setAppeal(appeal1);

        Appeal appeal2 = new Appeal();
        appeal2.setCreationDate(ZonedDateTime.now().minusYears(7));
        filePool2.setAppeal(appeal2);

        Appeal appeal3 = new Appeal();
        appeal3.setCreationDate(ZonedDateTime.now().minusYears(9));
        filePool3.setAppeal(appeal3);

        List<FilePool> filePools = new ArrayList<>();
        filePools.add(filePool1);
        filePools.add(filePool2);
        filePools.add(filePool3);

        // Мокируем метод и задаем его поведение
        when(filePoolRepository.findFilePoolByCreationDateBefore(any()))
                .thenReturn(filePools);

        // Выполняем тестирование
        List<UUID> result = filePoolService.getUUIDByCreationDateBeforeFiveYears();

        // Проверяем результат
        List<UUID> expected = Stream.of(uuid1, uuid2, uuid3).collect(Collectors.toList());
        assertEquals(expected, result);
    }
}