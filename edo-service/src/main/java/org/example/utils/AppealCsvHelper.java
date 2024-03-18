package org.example.utils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Component
public class AppealCsvHelper {

    private final QuestionService questionService;
    private final List<String> columns = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");

    /**
     * Поле задает формат даты в CSV
     * Можно изменить через setFormatter
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * Поле задает разделитель в списке вопросов
     * Можно изменить через setSeparator
     */
    private String questionsSeparator = ", ";
    @Autowired
    public AppealCsvHelper(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Метод для загрузки списка обращений в формате CSV по сети
     * @param appeals список обращений
     * @return ByteArrayInputStream
     * @throws IOException при ошибке создания CSV файла
     */
    public ByteArrayInputStream appealsToCsv(List<Appeal> appeals) throws IOException {
        final CSVFormat format = CSVFormat.DEFAULT;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            appealsToCsv(csvPrinter, appeals);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Ошибка создания CSV файла: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод для сохранения списка обращений в файл CSV
     * @param appeals список обращений
     * @param fileName имя файла в формате абсолютного пути
     * @throws IOException при ошибке записи в CSV файл
     */
    public void appealsToCsv(List<Appeal> appeals, String fileName) throws IOException {
        final CSVFormat format = CSVFormat.DEFAULT;
        boolean fileExists = new File(fileName).exists();

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName, fileExists), format)) {
            appealsToCsv(csvPrinter, appeals);
        } catch (IOException e) {
            log.error("Ошибка записи в CSV файл: " + e.getMessage());
        }
    }

    /**
     * Метод для формирования файла CSV из списка обращений
     * @param csvPrinter объект для записи
     * @param appeals список обращений
     * @throws IOException при ошибке формирования CSV
     */
    private void appealsToCsv(CSVPrinter csvPrinter, List<Appeal> appeals) throws IOException {
        log.info("Запись заголовка в CSV файл");
        csvPrinter.printRecord(columns);

        for (Appeal appeal : appeals) {
            List<String> data = Arrays.asList(
                    String.valueOf(appeal.getNumber() != null
                            ? appeal.getNumber()
                            : appeal.getId()),
                    formatter.format(appeal.getCreationDate()),
                    appeal.getCreator().getFioNominative(),
                    getQuestions(appeal.getId()),
                    appeal.getStatusType().getRusStatusType()
            );
            log.info("Запись обращения {} в CSV файл", appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId());

            csvPrinter.printRecord(data);
        }
        csvPrinter.flush();
    }

    /**
     * Метод для получения списка вопросов обращения
     * @param id идентификатор обращения
     * @return строку со списком вопросов
     */
    private String getQuestions(Long id) {
        log.info("Получение списка вопросов обращения из базы данных");
        return questionService.getAllQuestionsByAppealId(id)
                .stream()
                .map(Question::getSummary)
                .collect(Collectors.joining(questionsSeparator));
    }
}
