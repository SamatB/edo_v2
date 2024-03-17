package org.example.controller;

import feign.FeignException;
import org.apache.commons.io.FileUtils;
import org.example.dto.AppealDto;
import org.example.feign.AppealFeignClient;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса AppealController.
 */
public class AppealControllerTest {

    @Mock
    AppealFeignClient appealFeignClient;

    @InjectMocks
    AppealController appealController;

    @Mock
    FeignException.NotFound notFoundException;

    @Mock
    FeignException.BadRequest badRequestException;

    private static final String OUTPUT_DIR = "src/test/resources/";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода getAppeal.
     */
    @Test
    public void testGetAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.getAppeal(anyLong())).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.getAppeal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
        assertEquals(appealController.logCountOfRequestsToAppeal(), 1);

    }

    /**
     * Тест для метода getAppeal c несуществующим обращением.
     */
    @Test
    public void testGetAppealNotFound() {
        when(appealFeignClient.getAppeal(anyLong())).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.getAppeal(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода saveAppeal.
     */
    @Test
    public void testSaveAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.saveAppeal(any(AppealDto.class))).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.saveAppeal(appealDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода saveAppeal с нулевым обращением.
     */
    @Test
    public void testSaveAppealBadRequest() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.saveAppeal(any(AppealDto.class))).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.saveAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода archiveAppeal.
     */
    @Test
    public void testArchiveAppeal() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.archiveAppeal(anyLong())).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.archiveAppeal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода archiveAppeal с несуществующим обращением.
     */
    @Test
    public void testArchiveAppealNotFound() {
        when(appealFeignClient.archiveAppeal(anyLong())).thenReturn(null);

        ResponseEntity<AppealDto> response = appealController.archiveAppeal(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода registerAppeal.
     */
    @Test
    public void testRegisterAppeal() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNumber("АБВ-12345");
        when(appealFeignClient.registerAppeal(anyLong())).thenReturn(appealDto);

        ResponseEntity<AppealDto> response = appealController.registerAppeal(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appealDto, response.getBody());
    }

    /**
     * Тест для метода registerAppeal с несуществующим обращением.
     */
    @Test
    public void testRegisterAppealNotFound() {
        when(appealFeignClient.registerAppeal(anyLong())).thenThrow(notFoundException);

        ResponseEntity<AppealDto> response = appealController.registerAppeal(Long.MAX_VALUE);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Тест для метода registerAppeal - попытка регистрации зарегистрированного обращения.
     */
    @Test
    public void testRegisterAppealRepeatedRegistration() {
        when(appealFeignClient.registerAppeal(anyLong())).thenThrow(badRequestException);

        ResponseEntity<AppealDto> response = appealController.registerAppeal(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal.
     */
    @Test
    public void testReserveNumberForAppeal_Ok() {
        AppealDto appealDto = new AppealDto();
        AppealDto expected = new AppealDto();
        expected.setReservedNumber("АБВ-12345");
        when(appealFeignClient.reserveNumberForAppeal(any(AppealDto.class))).thenReturn(expected);
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(appealFeignClient, times(1)).reserveNumberForAppeal(appealDto);
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым обращением.
     */
    @Test
    public void testReserveNumberForAppeal_NullAppeal_BadRequest() {
        AppealDto appealDto = new AppealDto();
        when(appealFeignClient.reserveNumberForAppeal(eq(null))).thenThrow(badRequestException);

        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с нулевым nomenclature.
     */
    @Test
    public void testReserveNumberForAppeal_NullNomenclature_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNomenclature(null);
        when(appealFeignClient.reserveNumberForAppeal(appealDto)).thenThrow(badRequestException);
        ResponseEntity<AppealDto> response = appealController.reserveNumberForAppeal(appealDto);
        System.err.println(response.toString());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Тест для метода reserveNumberForAppeal с уже существующим номером.
     */
    @Test
    public void testReserveNumberForAppeal_ExistingNumber_BadRequest() {
        AppealDto appealDto = new AppealDto();
        appealDto.setNumber("АБВ-12345");
        when(appealFeignClient.reserveNumberForAppeal(appealDto)).thenThrow(badRequestException);
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
        when(appealFeignClient.reserveNumberForAppeal(appealDto)).thenThrow(badRequestException);
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
        when(appealFeignClient.getAllAppeals()).thenReturn(appealDtos);
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
        when(appealFeignClient.getAllAppeals()).thenReturn(appealDtos);
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
        byte[] bytes = mockInputStream.readAllBytes();

        when(appealFeignClient.downloadAppealsXlsxReport()).thenReturn(bytes);

        ResponseEntity<?> response = appealController.getAllAppealsAsXlsx();
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
        byte[] bytes = mockInputStream.readAllBytes();

        when(appealFeignClient.downloadAppealsXlsxReport()).thenReturn(bytes);

        ResponseEntity<?> response = appealController.getAllAppealsAsXlsx();
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
        when(appealFeignClient.downloadAppealsXlsxReport()).thenReturn(mockInputStream.readAllBytes());

        ResponseEntity<?> response = appealController.getAllAppealsAsXlsx();

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
        when(appealFeignClient.downloadAppealsXlsxReport()).thenReturn(null);

        ResponseEntity<?> response = appealController.getAllAppealsAsXlsx();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Тест для метода downloadAppealsXlsxReport.
     * Возвращает статус 404, если получен файл нулевой длины
     */
    @Test
    void downloadAppealsXlsxReport_returnsNotFound() {
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
        when(appealFeignClient.downloadAppealsXlsxReport()).thenReturn(mockInputStream.readAllBytes());

        ResponseEntity<?> response = appealController.getAllAppealsAsXlsx();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
