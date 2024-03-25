package org.example.utils;

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
    public static final List<String> APPEAL_REPORT_COLUMNS = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");
    public static final String COMMA_DELIMITER = ", ";
    public static final DateTimeFormatter DAY_FIRST_DATE_NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateFormat DATE_TIME_WITH_SECONDS_FORMATTER = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static String getAppealsXlsxReportFileName() {
        String currentDateTime = DATE_TIME_WITH_SECONDS_FORMATTER.format(new Date());
        return "appeals_" +
                currentDateTime +
                XLSX_FILE_EXTENSION;
    }
}
