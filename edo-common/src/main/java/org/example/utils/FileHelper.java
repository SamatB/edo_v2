package org.example.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Утилитный класс для формирования имени файлов.
 */
public class FileHelper {
    public static final String XLSX_FILE_EXTENSION = ".xlsx";
    public static final String APPEAL_SHEET_NAME_RUS = "Обращения";
    public static final List<String> APPEAL_REPORT_COLUMNS_RUS = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final List<String> APPEAL_REPORT_COLUMNS = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");
    public static final String COMMA_DELIMITER = ", ";
    public static final DateTimeFormatter DAY_FIRST_DATE_NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateFormat DATE_TIME_WITH_SECONDS_FORMATTER = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static String getAppealsCsvReportFileName() {
        String currentDateTime = DATE_TIME_WITH_SECONDS_FORMATTER.format(new Date());
        return "appeals_" +
                currentDateTime +
                CSV_FILE_EXTENSION;
    }

    public static String getAppealsXlsxReportFileName() {
        String currentDateTime = DATE_TIME_WITH_SECONDS_FORMATTER.format(new Date());
        return "appeals_" +
                currentDateTime +
                XLSX_FILE_EXTENSION;
    }

    /**
     * Метод для формирования успешного ответа, возвращающего файл в формате XLSX.
     *
     * @param file массив байтов с содержимым файла
     * @return ResponseEntity<byte [ ]>
     */
    public static ResponseEntity<byte[]> successResponseForXlsxReport(byte[] file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getAppealsXlsxReportFileName())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(file.length)
                .body(file);
    }

    /**
     * Метод для формирования успешного ответа, возвращающего файл в формате XLSX.
     * @param file ресурс с содержимым файла
     * @return ResponseEntity<Resource>
     */
    public static ResponseEntity<Resource> successResponseForXlsxReport(ByteArrayResource file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getAppealsXlsxReportFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .body(file);
    }
}
