package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.repository.AppealRepository;
import org.example.repository.QuestionRepository;
import org.example.service.ReportService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final AppealRepository appealRepository;
    private final QuestionRepository questionRepository;

    /**
     * Запись в CSV файл
     * Если название файла не заканчивается на .csv, то добавляется
     *
     * @param fileName название файла, в который будет записан файл
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

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT)) {
            log.info("Запись заголовка в CSV файл");
            csvPrinter.printRecord("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");
            for (Appeal appeal : appeals) {
                log.info("Получение списка вопросов обращения из базы данных");
                String questions = questionRepository.findAllByAppealId(appeal.getId())
                        .stream()
                        .map(Question::getSummary)
                        .collect(Collectors.joining(", "));
                log.info("Запись обращения {} в CSV файл", appeal.getNumber() != null
                        ? appeal.getNumber()
                        : appeal.getId());
                csvPrinter.printRecord((appeal.getNumber() != null
                        ? appeal.getNumber()
                        : appeal.getId()),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy").format(appeal.getCreationDate()),
                        appeal.getCreator().getFioNominative(),
                        questions,
                        appeal.getStatusType().getRusStatusType());
            }
        } catch (IOException e) {
            log.error("Ошибка записи в CSV ", e);
        }
    }
}
