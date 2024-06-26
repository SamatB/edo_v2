package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.service.AppealService;
import org.example.service.ReportService;
import org.example.utils.export.AppealExcelExporter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.example.utils.FileHelper.XLSX_FILE_EXTENSION;
import static org.example.utils.export.AppealCsvHelper.appealsToCsv;
import static org.example.utils.FileHelper.CSV_FILE_EXTENSION;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final AppealService appealService;

    /**
     * Запись в XLSX файл
     * Если название файла не заканчивается на .xlsx, то добавляется
     *
     * @param fileName название файла, в который будет записан файл
     */
    public void writeAppealsToXlsx(int offset, int size, String fileName) {
        log.info("Начало записи в XLSX файл... {}", fileName);
        if (!fileName.endsWith(XLSX_FILE_EXTENSION)) {
            log.info("Добавление суффикса {} к имени файла", XLSX_FILE_EXTENSION);
            fileName = fileName + XLSX_FILE_EXTENSION;
        }
        log.info("Получение списка обращений из базы данных");
        List<AppealDto> appeals = appealService.getPaginatedAppeals(offset, size);

        AppealExcelExporter appealExcelExporter = new AppealExcelExporter();

        try {
            log.info("Запись в XLSX файл {}", fileName);
            appealExcelExporter.exportAppealsToXlsx(fileName, appeals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Загрузка XLSX файла
     *
     * @return ByteArrayInputStream
     */
    public ByteArrayInputStream getAppealsXlsxReport(int offset, int size) {
        log.info("Получение списка обращений из базы данных");
        List<AppealDto> appeals = appealService.getPaginatedAppeals(offset, size);
        AppealExcelExporter appealExcelExporter = new AppealExcelExporter();

        try {
            log.info("Загрузка XLSX файла...");
            return appealExcelExporter.exportAppealsToXlsx(appeals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Запись в CSV файл
     * Если название файла не заканчивается на .csv, то добавляется
     *
     * @param fileName название файла для записи в формате абсолютного пути
     */
    public void writeAppealsToCsv(int offset, int size, String fileName) {
        log.info("Запись в CSV файл");
        if (!fileName.endsWith(CSV_FILE_EXTENSION)) {
            log.info("Добавление суффикса {} к имени файла", CSV_FILE_EXTENSION);
            fileName = fileName + CSV_FILE_EXTENSION;
        }
        log.info("Получение списка обращений из базы данных");
        List<AppealDto> appeals = appealService.getPaginatedAppeals(offset, size);

        try {
            log.info("Создание CSV файла...");
            appealsToCsv(appeals, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Скачивание CSV файла
     *
     * @return ByteArrayInputStream
     */
    public ByteArrayInputStream downloadAppealsCsvReport(int offset, int size) {
        log.info("Получение списка обращений из базы данных");
        List<AppealDto> appeals = appealService.getPaginatedAppeals(offset, size);
        try {
            log.info("Загрузка CSV файла...");
            return appealsToCsv(appeals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
