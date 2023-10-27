package org.example;

import org.example.controller.FileController;
import org.example.service.FileStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестирование классов SaveFileController и GetFileController.
 * Данный код представляет тестовый класс для проверки функциональности классов SaveFileController и GetFileController.
 * В тестах используется заглушка (mock) для FileStorageService
 * и проверяется успешное сохранение файла (saveFile_Success),
 * обработка ошибки при сохранении файла (saveFile_Error),
 * успешное получение файла (getFile_Success)
 * и случай, когда файл не найден (getFile_NotFound).
 */
@SpringBootTest
public class EdoFileStorageApplicationTests {
    /**
     * Тесты для SaveFileController.
     */
    /**
     * Тест успешного сохранения файла.
     */
    @Test
    void saveFile_Success() {
        // Создаем заглушку для FileStorageService
        FileStorageService FileStorageService = mock(FileStorageService.class);
        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(FileStorageService);
        // Создаем тестовый файл
        MultipartFile file = new MockMultipartFile("testFile.txt", "Hello, World!".getBytes());
        // Устанавливаем поведение заглушки
        when(FileStorageService.saveFile(file)).thenReturn(ResponseEntity.ok("file-uuid"));
        // Вызываем метод контроллера
        ResponseEntity<String> response = controller.saveFile(file);
        // Проверяем, что полученный ответ содержит ожидаемый UUID и статус 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("file-uuid", response.getBody());
    }

    /**
     * Тест ошибки при сохранении файла.
     */
    @Test
    void saveFile_Error() {
        // Создаем заглушку для FileStorageService
        FileStorageService fileStorageService = mock(FileStorageService.class);
        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(fileStorageService);
        // Создаем тестовый файл
        MultipartFile file = new MockMultipartFile("testFile.txt", "Hello, World!".getBytes());
        // Устанавливаем поведение заглушки
        when(fileStorageService.saveFile(file)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        // Вызываем метод контроллера
        ResponseEntity<String> response = controller.saveFile(file);
        // Проверяем, что полученный ответ имеет статус 500 Internal Server Error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Тесты для GetFileController.
     */
    /**
     * Тест успешного получения файла.
     */
    @Test
    void getFile_Success() {
        // Создаем заглушку для FileStorageService
        FileStorageService fileStorageService = mock(FileStorageService.class);
        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(fileStorageService);
        // Задаем UUID файла для теста
        String uuid = "file-uuid";
        // Создаем тестовый ресурс (файл)
        byte[] fileContent = "Hello, World!".getBytes();
        Resource fileResource = new ByteArrayResource(fileContent);
        // Устанавливаем поведение заглушки
        when(fileStorageService.getFile(uuid)).thenReturn(ResponseEntity.ok(fileResource));
        // Вызываем метод контроллера
        ResponseEntity<Resource> response = controller.getFile(uuid);
        // Проверяем, что полученный ответ содержит ожидаемый ресурс (файл) и статус 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        try {
            assertEquals(fileContent.length, Objects.requireNonNull(response.getBody()).contentLength());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Тест получения файла, когда файл не найден.
     */
    @Test
    void getFile_NotFound() {
        // Создаем заглушку для FileStorageService
        FileStorageService fileStorageService = mock(FileStorageService.class);
        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(fileStorageService);
        // Задаем UUID файла для теста
        String uuid = "non-existing-file-uuid";
        // Устанавливаем поведение заглушки
        when(fileStorageService.getFile(uuid)).thenReturn(ResponseEntity.notFound().build());
        // Вызываем метод контроллера
        ResponseEntity<Resource> response = controller.getFile(uuid);
        // Проверяем, что полученный ответ имеет статус 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Тест удачного удаления старых файлов из MinIO.
     */
    @Test
    @DisplayName("Should return a response with status 200 when the delete operation is success")
    public void testDeleteOldFiles_Success() {
        // Создаем заглушку для FileStorageService
        FileStorageService fileStorageService = mock(FileStorageService.class);

        // Задаем UUID файлов для теста
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(UUID.randomUUID());
        uuidList.add(UUID.randomUUID());

        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(fileStorageService);

        when(fileStorageService.deleteOldestThanFiveYearsFiles(uuidList)).thenReturn(ResponseEntity.status(200).build());

        ResponseEntity<HttpStatus> response = controller.deleteOldFiles(uuidList);

        assertEquals(ResponseEntity.status(200).build(), response);
    }

    /**
     * Тест удаления старых файлов из MinIO c ошибкой.
     */
    @Test
    @DisplayName("Should return a bad_request response when happened Server Error")
    public void testDeleteOldFiles_Exception(){
        // Создаем заглушку для FileStorageService
        FileStorageService fileStorageService = mock(FileStorageService.class);

        // Задаем UUID файлов для теста
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(UUID.randomUUID());
        uuidList.add(UUID.randomUUID());

        // Создаем экземпляр контроллера и передаем заглушку в конструктор
        FileController controller = new FileController(fileStorageService);

        when(fileStorageService.deleteOldestThanFiveYearsFiles(uuidList)).thenThrow();

        ResponseEntity response = controller.deleteOldFiles(uuidList);

        assertEquals(ResponseEntity.status(500).build(), response);
    }
}