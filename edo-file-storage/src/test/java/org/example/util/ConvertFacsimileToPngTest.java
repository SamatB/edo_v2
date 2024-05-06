package org.example.util;

import org.example.utils.FileHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тесты для {@link ConvertFacsimileToPng}
 */
@SpringBootTest
class ConvertFacsimileToPngTest {
    private static final String OUTPUT_DIR = "src/test/resources/";

    @Test
    @DisplayName("Успешная конвертация PNG с непрозрачным фоном")
    void saveFileWithFacsimilePng_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.png", "image/png", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.png")));

        InputStream result = ConvertFacsimileToPng.convertToPng(mockFile);

        assertNotNull(result);
        assertThat(result.available()).isGreaterThan(0);

        FileHelper.saveInputStreamToFile(result, OUTPUT_DIR + "test_png_converted.png");
    }

    @Test
    @DisplayName("Успешная конвертация JPG в PNG")
    void saveFileWithFacsimileJpg_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.jpg")));

        InputStream result = ConvertFacsimileToPng.convertToPng(mockFile);

        assertNotNull(result);
        assertThat(result.available()).isGreaterThan(0);

        FileHelper.saveInputStreamToFile(result, OUTPUT_DIR + "test_jpg_converted.jpg");
    }

    @Test
    @DisplayName("Успешная конвертация JPEG в PNG")
    void saveFileWithFacsimileJpeg_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpeg", "image/jpeg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.jpeg")));

        InputStream result = ConvertFacsimileToPng.convertToPng(mockFile);

        assertNotNull(result);
        assertThat(result.available()).isGreaterThan(0);

        FileHelper.saveInputStreamToFile(result, OUTPUT_DIR + "test_jpeg_converted.jpeg");
    }

    @Test
    @DisplayName("Успешная конвертация файла с непрозрачным \"грязным\" фоном")
    void dirtyImageConversionToPng_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "dirty_test.png", "image/png", Files.readAllBytes(Paths.get(OUTPUT_DIR + "dirty_test.png")));

        InputStream result = ConvertFacsimileToPng.convertToPng(mockFile);

        assertNotNull(result);
        assertThat(result.available()).isGreaterThan(0);

        FileHelper.saveInputStreamToFile(result, OUTPUT_DIR + "dirty_test_png_converted.png");
    }

    @Test
    @DisplayName("Файл с прозрачным фоном не требует конвертации")
    void alreadyTransparentBackgroundNoConversion_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "alpha_test.png", "image/png", Files.readAllBytes(Paths.get(OUTPUT_DIR + "alpha_test.png")));

        InputStream result = ConvertFacsimileToPng.convertToPng(mockFile);

        assertNotNull(result);
        assertThat(result.available()).isGreaterThan(0);

        FileHelper.saveInputStreamToFile(result, OUTPUT_DIR + "alpha_test_png_converted.png");
    }
}
