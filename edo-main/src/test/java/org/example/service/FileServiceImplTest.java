package org.example.service;

import org.example.dto.FilePoolDto;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.example.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса FileServiceImpl.
 */
@DisplayName("Тестирование сервиса для работы с файлами")
class FileServiceImplTest {
    @Mock
    private FileFeignClient fileFeignClient;
    @Mock
    private FilePoolFeignClient filePoolFeignClient;
    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверка на возврат null, когда статус ответа не 2хх
     */
    @Test
    @DisplayName("Should return null when response status code is not 2xx")
    void saveFileWhenResponseIsNotSuccessful() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(fileFeignClient.saveFile(multipartFile)).thenReturn(response);

        ResponseEntity<String> result = fileService.saveFile(multipartFile);

        assertNull(result);
        verify(fileFeignClient, times(1)).saveFile(multipartFile);
        verify(filePoolFeignClient, never()).saveFile(any(FilePoolDto.class));
    }

    /**
     * Проверка на успешное сохранение файла
     */
    @Test
    @DisplayName("Should save the file and return response with status code 2xx")
    void saveFileWhenResponseIsSuccessful() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("testfile.txt");
        when(multipartFile.getSize()).thenReturn(1024L);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("12345678-1234-1234-1234-1234567890ab", HttpStatus.OK);
        when(fileFeignClient.saveFile(multipartFile)).thenReturn(responseEntity);

        ResponseEntity<String> response = fileService.saveFile(multipartFile);

        verify(fileFeignClient, times(1)).saveFile(multipartFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12345678-1234-1234-1234-1234567890ab", response.getBody());

        verify(filePoolFeignClient, times(1)).saveFile(any(FilePoolDto.class));
    }

    /**
     * Проверка на сохранение объекта FilePoolDto с корректными параметрами
     */
    @Test
    @DisplayName("Should save the file with correct file parameters")
    void saveFileWithCorrectFileSize() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("testfile.txt");
        when(multipartFile.getSize()).thenReturn(1024L);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("12345678-1234-1234-1234-1234567890ab", HttpStatus.OK);
        when(fileFeignClient.saveFile(multipartFile)).thenReturn(responseEntity);

        ResponseEntity<String> response = fileService.saveFile(multipartFile);

        verify(fileFeignClient, times(1)).saveFile(multipartFile);

        verify(filePoolFeignClient, times(1)).saveFile(
                ArgumentMatchers.argThat(dto ->
                        dto.getStorageFileId().equals(UUID.fromString(response.getBody())) &&
                                dto.getName().equals("testfile.txt") &&
                                dto.getExtension().equals("txt") &&
                                dto.getStorageFileId().toString().equals("12345678-1234-1234-1234-1234567890ab") &&
                                dto.getSize() == 1024L &&
                                dto.getPageCount() == 1 &&
                                dto.getUploadDate() != null && // Ensure the uploadDate is not null
                                dto.getArchivedDate() == null // Ensure the archivedDate is null
                )
        );
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}