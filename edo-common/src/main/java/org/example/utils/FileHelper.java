package org.example.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Утилитный класс для формирования имени файлов.
 */
public class FileHelper {
    public static final String XLSX_FILE_EXTENSION = ".xlsx";
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String PNG_FILE_EXTENSION = "png";

    public static final String COMMA_DELIMITER = ", ";
    public static final DateTimeFormatter DAY_FIRST_DATE_NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateFormat DATE_TIME_WITH_SECONDS_FORMATTER = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static final String APPEAL_SHEET_NAME_RUS = "Обращения";
    public static final List<String> APPEAL_REPORT_COLUMNS_RUS = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");
    public static final List<String> APPEAL_REPORT_COLUMNS = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");

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

    /**
     * Метод для формирования успешного ответа.
     */
    public static ResponseEntity<byte[]> successResponseForAppealsCsvReport(byte[] file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getAppealsCsvReportFileName())
                .contentType(MediaType.parseMediaType("application/csv"))
                .contentLength(file.length)
                .body(file);
    }

    public static ResponseEntity<ByteArrayResource> successResponseForAppealsCsvReport(ByteArrayResource file) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getAppealsCsvReportFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .body(file);
    }

    /**
     * @param multipartFile - файл, конвертируемый в BufferedImage
     * @return BufferedImage - класс-реализация интерфейса Image для работы с изображениями
     */
    public static BufferedImage getConvertedBufferedImage(MultipartFile multipartFile) {
        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new IllegalArgumentException("MultipartFile is null or empty");
            }
            if (!Objects.requireNonNull(multipartFile.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Invalid image file type");
            }
            return ImageIO.read(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file", e);
        }
    }

    /**
     * Сохраняет поток ввода в файл.
     * @param inputStream Поток ввода данных для сохранения.
     * @param fileName Имя файла для сохранения данных в формате абсолютного пути.
     * @throws Exception Может выбросить исключение ввода-вывода или другие исключения.
     */
    public static void saveInputStreamToFile(InputStream inputStream, String fileName) throws Exception {
        File outputFile = new File(fileName);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        }
    }
}
