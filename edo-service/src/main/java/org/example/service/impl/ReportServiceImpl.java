package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Appeal;
import org.example.repository.AppealRepository;
import org.example.service.ReportService;
import org.example.utils.AppealCsvHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final AppealRepository appealRepository;
    private final AppealCsvHelper appealCsvHelper;

    /**
     * Запись в CSV файл
     * Если название файла не заканчивается на .csv, то добавляется
     *
     * @param fileName название файла для записи в формате абсолютного пути
     */
    public void writeAppealsToCsv(String fileName) {
        log.info("Запись в CSV файл");
        String suffix = ".csv";
        if (!fileName.endsWith(suffix)) {
            log.info("Добавление суффикса {} к имени файла", suffix);
            fileName = fileName + suffix;
        }
        log.info("Получение списка обращений из базы данных");
        List<Appeal> appeals = appealRepository.findAll();

        try {
            log.info("Создание CSV файла...");
//            appealCsvHelper.setFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
//            appealCsvHelper.setQuestionsSeparator("; ");
            appealCsvHelper.appealsToCsv(appeals, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Скачивание CSV файла
     *
     * @return ByteArrayInputStream
     */
    public ByteArrayInputStream downloadAppealsCsvReport() {
        log.info("Получение списка обращений из базы данных");
        List<Appeal> appeals = appealRepository.findAll();
        try {
            log.info("Загрузка CSV файла...");
            return appealCsvHelper.appealsToCsv(appeals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
