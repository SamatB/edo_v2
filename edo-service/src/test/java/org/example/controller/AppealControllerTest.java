package org.example.controller;

import org.apache.commons.io.FileUtils;
import org.example.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AppealControllerTest {

    @Mock
    ReportService reportService;
    @InjectMocks
    AppealController appealController;
    private static final String OUTPUT_DIR = "src/test/resources/";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода downloadAppealsCsvReport.
     * Возвращает статус 200, если файл был получен.
     */
    @Test
    void downloadAppealsCsvReport_returnsOk() throws IOException {
        File testCsv = new File(OUTPUT_DIR + "appeals_2024-03-18_19_25_07.csv");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testCsv));

        when(reportService.downloadAppealsCsvReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsCsvReport();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Тест для метода downloadAppealsCsvReport.
     * Проверяет, что тело ответа содержит правильную длину
     */
    @Test
    void downloadAppealsCsvReport_responseHasBodyWithCorrectContentLength() throws IOException {
        File testCsv = new File(OUTPUT_DIR + "appeals_2024-03-18_19_25_07.csv");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testCsv));
        int size = mockInputStream.available();
        when(reportService.downloadAppealsCsvReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsCsvReport();
        assertEquals(size, response.getHeaders().getContentLength());
        assertThat(response.getBody()).isNotNull();
    }

    /**
     * Тест для метода downloadAppealsCsvReport.
     * Проверяет формат имени файла
     */
    @Test
    void downloadAppealsCsvReport_fileHasSuffix() throws IOException {
        File testCsv = new File(OUTPUT_DIR + "appeals_2024-03-18_19_25_07.csv");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testCsv));

        when(reportService.downloadAppealsCsvReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsCsvReport();

        assertThat(response.getHeaders().containsKey("Content-Disposition")).isTrue();
        assertThat(Objects.requireNonNull(response.getHeaders().get("Content-Disposition")).get(0)).endsWithIgnoringCase(".csv");
        assertThat(Objects.requireNonNull(response.getHeaders().get("Content-Disposition")).get(0)).startsWithIgnoringCase("attachment; filename=");
    }

    /**
     * Тест для метода downloadAppealsCsvReport.
     * Проверяет, что в случае ошибки открытия потока ввода данных, возвращается статус 500
     */
    @Test
    void downloadAppealsCsvReport_returnsInternalServerError() {
        when(reportService.downloadAppealsCsvReport()).thenReturn(null);

        ResponseEntity<?> response = appealController.downloadAppealsCsvReport();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Тест для метода downloadAppealsCsvReport.
     * Возвращает статус 404, если получен файл нулевой длины
     */
    @Test
    void downloadAppealsCsvReport_returnsNotFound() {
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
        when(reportService.downloadAppealsCsvReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsCsvReport();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}