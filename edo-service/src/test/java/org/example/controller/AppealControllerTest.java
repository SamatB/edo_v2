package org.example.controller;

import jakarta.persistence.EntityExistsException;
import org.example.dto.AppealDto;
import org.example.service.impl.AppealServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.example.dto.AppealDto;
import org.example.service.AppealService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса AppealController.
 */
class AppealControllerTest {
    @Mock
    AppealService appealService;
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
     * Тест для метода reserveNumberForAppeal.
     */
    @Test
    public void testReserveNumberForAppeal_Ok() {
        AppealDto appealDto = new AppealDto();
        AppealDto expected = new AppealDto();
        expected.setReservedNumber("АБВ-12345");

        when(appealService.reserveNumberForAppeal(any(AppealDto.class))).thenReturn(expected);
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(appealService, times(1)).reserveNumberForAppeal(appealDto);
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым обращением.
     */
    @Test
    public void testReserveNumberForAppeal_NullAppeal_BadRequest() {
        when(appealService.reserveNumberForAppeal(eq(null))).thenThrow(new IllegalArgumentException());

        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым nomenclature.
     */
    @Test
    public void testReserveNumberForAppeal_NullNomenclature_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNomenclature(null);
        appealDto.setAnnotation("Тестовое обращение");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new IllegalArgumentException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже существующим номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingNumber_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNumber("АБВ-12345");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new EntityExistsException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже зарезервированным номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingReservedNumber_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setReservedNumber("АБВ-12345");
        when(appealService.reserveNumberForAppeal(appealDto)).thenThrow(new EntityExistsException());
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода getAllAppeals.
     * Возвращает список обращений со статусом 200.
     */
    @Test
    void getAllAppeals_returnsOk() {
        AppealDto appealDto1 = new AppealDto();
        AppealDto appealDto2 = new AppealDto();
        List<AppealDto> appealDtos = new ArrayList<>(Arrays.asList(appealDto1, appealDto2));
        when(appealService.getAllAppeals()).thenReturn(appealDtos);
        ResponseEntity<?> response = appealController.getAllAppeals();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDtos, response.getBody());
    }

    /**
     * Тест для метода getAllAppeals.
     * Возвращает NoContent если список обращений пуст (со статусом 204).
     */
    @Test
    void getAllAppeals_returnsNoContent() {
        List<AppealDto> appealDtos = new ArrayList<>();
        when(appealService.getAllAppeals()).thenReturn(appealDtos);
        ResponseEntity<?> response = appealController.getAllAppeals();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.
     * Возвращает статус 200, если файл был получен.
     */
    @Test
    void downloadAppealsXlsxReport_returnsOk() throws IOException {
        File testXlsx = new File(OUTPUT_DIR + "appeals_2024-03-17_16_03_38.xlsx");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testXlsx));

        when(reportService.getAppealsXlsxReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsXlsxReport();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.*
     * Проверяет, что тело ответа содержит правильную длину
     */
    @Test
    void downloadAppealsXlsxReport_responseHasBodyWithCorrectContentLength() throws IOException {
        File testXlsx = new File(OUTPUT_DIR + "appeals_2024-03-17_16_03_38.xlsx");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testXlsx));
        int size = mockInputStream.available();
        when(reportService.getAppealsXlsxReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsXlsxReport();
        assertEquals(size, response.getHeaders().getContentLength());
        assertThat(response.getBody()).isNotNull();
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.
     * Проверяет формат имени файла
     */
    @Test
    void downloadAppealsXlsxReport_fileHasSuffix() throws IOException {
        File testXlsx = new File(OUTPUT_DIR + "appeals_2024-03-17_16_03_38.xlsx");
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(testXlsx));

        when(reportService.getAppealsXlsxReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsXlsxReport();

        assertThat(response.getHeaders().containsKey("Content-Disposition")).isTrue();
        assertThat(Objects.requireNonNull(response.getHeaders().get("Content-Disposition")).get(0)).endsWithIgnoringCase(".xlsx");
        assertThat(Objects.requireNonNull(response.getHeaders().get("Content-Disposition")).get(0)).startsWithIgnoringCase("attachment; filename=");
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.
     * Проверяет, что в случае ошибки открытия потока ввода данных, возвращается статус 500
     */
    @Test
    void downloadAppealsXlsxReport_returnsInternalServerError() {
        when(reportService.getAppealsXlsxReport()).thenReturn(null);

        ResponseEntity<?> response = appealController.downloadAppealsXlsxReport();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.
     * Возвращает статус 404, если получен файл нулевой длины
     */
    @Test
    void downloadAppealsXlsxReport_returnsNotFound() {
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
        when(reportService.getAppealsXlsxReport()).thenReturn(mockInputStream);

        ResponseEntity<?> response = appealController.downloadAppealsXlsxReport();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}