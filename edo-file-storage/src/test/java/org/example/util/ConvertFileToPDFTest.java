package org.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.assertThat;




/**
 * Тестовый класс для {@link ConvertFileToPDF}.
 * Проводит тестирование функциональности конвертации документов Word и изображений в PDF.
 */
@SpringBootTest
public class ConvertFileToPDFTest {




    private static final String OUTPUT_DIR = "src/test/resources/";

    /**
     * Подготовка среды перед каждым тестом. Удаляет результаты предыдущих тестовых запусков, если они существуют.
     */
    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test_document.dif"));
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test_image.dif"));
    }

    /**
     * Тестирует конвертацию документа DOCX в PDF.
     * Проверяет, что результат не null и что поток данных не пустой.
     */
    @Test
    void testDocToPdfConversion() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test_document.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test_document.docx")));

        InputStream result = ConvertFileToPDF.convertToPDF(mockFile);

        assertThat(result).isNotNull();
        assertThat(result.available()).isGreaterThan(0);

        saveToFile(result, "test_document_converted.pdf");
    }

    /**
     * Тестирует конвертацию изображения JPEG в PDF.
     * Проверяет, что результат не null и что поток данных не пустой.
     */
    @Test
    void testImageToPdfConversion() throws Exception {
        MultipartFile mockImage = new MockMultipartFile("image", "test_image.jpeg", "image/jpeg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test_image.jpeg")));

        InputStream result = ConvertFileToPDF.convertToPDF(mockImage);

        assertThat(result).isNotNull();
        assertThat(result.available()).isGreaterThan(0);

        saveToFile(result, "test_images_converted.pdf");
    }

    /**
     * Сохраняет поток ввода в файл.
     * @param inputStream Поток ввода данных для сохранения.
     * @param fileName Имя файла для сохранения данных.
     * @throws Exception Может выбросить исключение ввода-вывода.
     */
    private void saveToFile(InputStream inputStream, String fileName) throws Exception {
        File outputFile = new File(OUTPUT_DIR + fileName);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

    /**
     * Тестирует попытку конвертации не DOC, DOCX, JPEG, PNG в PDF файла и проверяет, что содержимое потока соответствует ожидаемому.
     */
    @Test
    public void testFileNotPDFConversion() throws IOException {
        MultipartFile mockTextFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        InputStream result = ConvertFileToPDF.convertToPDF(mockTextFile);

        assertThat(result).isNotNull();

        byte[] resultData = result.readAllBytes();
        assertThat(new String(resultData)).isEqualTo("Hello, World!");
    }
}
