package org.example.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Утилитный класс для формирования имени файлов.
 */
public class FileHelper {
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final DateTimeFormatter dateNoTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateFormat dateTimeWithSecondsFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static String getAppealsCsvReportFileName() {
        String currentDateTime = dateTimeWithSecondsFormatter.format(new Date());
        return "appeals_" +
                currentDateTime +
                CSV_FILE_EXTENSION;
    }
}
