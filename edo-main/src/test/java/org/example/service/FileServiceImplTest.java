package org.example.service;

import lombok.SneakyThrows;
import org.example.dto.FilePoolDto;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.example.service.impl.FileServiceImpl;
import org.example.utils.FilePoolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    void saveFileWhenResponseIsNotSuccessful() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(fileFeignClient.saveFile(multipartFile)).thenReturn(response);

        ResponseEntity<String> result = fileService.saveFile(multipartFile, FilePoolType.MAIN);

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

        ResponseEntity<String> responseEntity = new ResponseEntity<>("12345678-1234-1234-1234-1234567890ab", HttpStatus.OK);
        when(fileFeignClient.saveFile(multipartFile)).thenReturn(responseEntity);

        ResponseEntity<String> response = fileService.saveFile(multipartFile, FilePoolType.MAIN);

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
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getSize()).thenReturn(1024L);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("12345678-1234-1234-1234-1234567890ab", HttpStatus.OK);
        when(fileFeignClient.saveFile(multipartFile)).thenReturn(responseEntity);

        ResponseEntity<String> response = fileService.saveFile(multipartFile, FilePoolType.MAIN);

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


    /**
     * выбрасывает NullPointerException при подаче в метод getConvertedBufferedImage()
     * файла с неправильным content-type(не "image/")
     */
    @Test
    @SneakyThrows
    @DisplayName("Should throw an IllegalArgumentException when an invalid image file is provided")
    void getConvertedBufferedImageWhenInvalidImageFileIsProvidedThenThrowIllegalArgumentException() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getContentType()).thenReturn("imege/txt");


        assertThrows(IllegalArgumentException.class, () -> {
            fileService.getConvertedBufferedImage(multipartFile);
        });
    }
    /**
     * Начало тестов на валидацию с реальными изображениями.
     * Тест что оба нужных для тестов изображения на месте.
     */
    @Test
    @DisplayName("Show that test images exist.")
    @SneakyThrows
    void imagesExistAndNotNull() {
        File goodImage = new File("src/test/resources/validpicture.jpg");
        File badImage = new File("src/test/resources/validpicture.jpg");
        BufferedImage goodBimage = ImageIO.read(goodImage);
        BufferedImage badBimage = ImageIO.read(badImage);

        assertAll(() -> {
            assertNotNull(goodBimage);
            assertNotNull(badBimage);
        });
    }

    /**
     * Тест метода проверки конвертации изображения
     */
    @Test
    @SneakyThrows
    @DisplayName("Should return a buffered image when a valid image file is provided")
    void getConvertedBufferedImageWhenValidImageFileIsProvided() {
        // перегоняем тестовое изображение из папки в массив байт с помощью метода снизу
        // и засовываем это изображение в MultipartFile
        byte[] imageBytes = byteArrayIntoImage("src/test/resources/validpicture.jpg");
        MultipartFile validMultipartFile = new MockMultipartFile(
                "testpicture", "validpicture.jpg", "image/jpg", imageBytes);
        BufferedImage expected = ImageIO.read(validMultipartFile.getInputStream());

        // Вызываем тестируемый метод
        BufferedImage real = fileService.getConvertedBufferedImage(validMultipartFile);

        // Проверяем результат
        assertAll(() -> {
            assertNotNull(real);
            assertEquals(expected.getHeight(), real.getHeight());
            assertEquals(expected.getWidth(), real.getWidth());
        });
    }

    /**
     * тест метода валидации изображений. Валидное изображение ((100x100 px.) , image/jpg) должно пройти
     * невалидное соответственно нет. Если одно из условий не выполнено тест падает.
     */
    @Test
    @SneakyThrows
    @DisplayName("Should pass valid image and not pass invalid. Valid picture is: (100x100 px.) , image/jpg")
    void validImagePassed() {
        // перегоняем тестовое изображение из папки в массив байт с помощью метода снизу и засовываем это изображение в MultipartFile
        byte[] validImageByteArray = byteArrayIntoImage("src/test/resources/validpicture.jpg");
        byte[] invalidImageByteArray = byteArrayIntoImage("src/test/resources/invalidpicture.jpg");
        MultipartFile validMultipartFile = new MockMultipartFile(
                "testpicture", "validpicture.jpg", "image/jpg", validImageByteArray);
        MultipartFile invalidMultipartFile = new MockMultipartFile(
                "testInvaldiPicture", "invalidpicture.jpg", "image/jpg", invalidImageByteArray);

        // Вызываем тестируемые методы
        Boolean pictureValid = fileService.validateFacsimileFile(validMultipartFile);
        Boolean pictureInvalid = fileService.validateFacsimileFile(invalidMultipartFile);

        // Проверяем результат
        assertAll(() -> {
            assertEquals(pictureValid, true);
            assertEquals(pictureInvalid, false);
        });
    }

    @SneakyThrows
    byte[] byteArrayIntoImage(String filepath) {
        File imageFile = new File(filepath);
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

}