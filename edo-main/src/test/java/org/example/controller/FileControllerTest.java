package org.example.controller;

import org.example.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса FileController.
 */
@DisplayName("Тестирование контроллера для работы с файлами")
class FileControllerTest {
    @Mock
    private FileServiceImpl fileService;

    @InjectMocks
    FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода saveFile c mock-заглушкой вместо MultipartFile
     */
    @Test
    void saveFileMock() {
        MultipartFile mockFile = mock(MultipartFile.class);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("File saved successfully");

        when(fileService.saveFile(mockFile)).thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = fileController.saveFile(mockFile);

        assertEquals(expectedResponse, actualResponse);
        verify(fileService, times(1)).saveFile(mockFile);
    }

    /**
     * Тест для метода saveFile c реальным объектом MultipartFile
     */
    @Test
    void saveFileWhenFileIsValid() {
        MultipartFile mockFile = new MockMultipartFile(
                "testFile", "testFile.txt", "text/plain", "Test file content".getBytes());

        when(fileService.saveFile(mockFile)).thenReturn(ResponseEntity.ok("File saved successfully"));

        ResponseEntity<String> response = fileController.saveFile(mockFile);

        verify(fileService, times(1)).saveFile(mockFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File saved successfully", response.getBody());
    }

}