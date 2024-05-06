package org.example.service;

import org.example.utils.FilePoolType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FileStorageServiceImplTest {
    @Autowired
    FileStorageService fileStorageService;
    private static final String OUTPUT_DIR = "src/test/resources/";

    @Test
    void saveFileWithFacsimilePng_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.png", "image/png", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.png")));

        ResponseEntity<String> result = fileStorageService.saveFile(mockFile, FilePoolType.FACSIMILE);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void saveFileWithFacsimileJpg_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.jpg")));

        ResponseEntity<String> result = fileStorageService.saveFile(mockFile, FilePoolType.FACSIMILE);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void saveFileWithFacsimileJpeg_success() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpeg", "image/jpeg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test.jpeg")));

        ResponseEntity<String> result = fileStorageService.saveFile(mockFile, FilePoolType.FACSIMILE);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDocToPdfConversion() throws Exception {
        MultipartFile mockFile = new MockMultipartFile("file", "test_document.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test_document.docx")));

        ResponseEntity<String> result = fileStorageService.saveFile(mockFile, FilePoolType.MAIN);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testImageToPdfConversion() throws Exception {
        MultipartFile mockImage = new MockMultipartFile("image", "test_image.jpeg", "image/jpeg", Files.readAllBytes(Paths.get(OUTPUT_DIR + "test_image.jpeg")));

        ResponseEntity<String> result = fileStorageService.saveFile(mockImage, FilePoolType.MAIN);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}