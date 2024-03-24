package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.dto.AppealDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.FileHelper.dateNoTimeFormatter;
import static org.example.utils.FileHelper.getAppealsCsvReportFileName;

@Slf4j
public class AppealCsvHelper {
    private static final List<String> columns = Arrays.asList("Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения");

    /**
     * Поле задает разделитель в списке вопросов
     */
    private static final String QUESTIONS_DELIMITER = ", ";
//    private final QuestionService questionService;
//    @Autowired
//    public AppealCsvHelper(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    /**
     * Метод для загрузки списка обращений в формате CSV по сети
     * @param appeals список обращений
     * @return ByteArrayInputStream
     * @throws IOException при ошибке создания CSV файла
     */
    public static ByteArrayInputStream appealsToCsv(List<AppealDto> appeals) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT)) {

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
    public static void appealsToCsv(List<AppealDto> appeals, String fileName) throws IOException {
        boolean fileExists = new File(fileName).exists();

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileName, fileExists), CSVFormat.DEFAULT)) {
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
    private static void appealsToCsv(CSVPrinter csvPrinter, List<AppealDto> appeals) throws IOException {
        log.info("Запись заголовка в CSV файл");
        csvPrinter.printRecord(columns);

        appeals.forEach(appeal -> {
            List<String> data = Arrays.asList(
                    String.valueOf(appeal.getNumber() != null
                            ? appeal.getNumber()
                            : appeal.getId()),
                    dateNoTimeFormatter.format(appeal.getCreationDate()),
                    appeal.getCreator().getFioNominative(),
//appeal.getQuestions().stream().map(QuestionDto::getSummary).collect(Collectors.joining(QUESTIONS_DELIMITER)),
//                    getQuestions(appeal.getId()),
                    "Question 1, Question 2, Question 3",
                    (appeal.getStatusType() != null ? appeal.getStatusType().getRusStatusType() : "Статус недоступен")
            );
            log.info("Запись обращения {} в CSV файл", appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId());

            try {
                csvPrinter.printRecord(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        csvPrinter.flush();
    }

    /**
     * Метод для получения списка вопросов обращения
     * @param id идентификатор обращения
     * @return строку со списком вопросов
     */
//    private String getQuestions(Long id) {
//        log.info("Получение списка вопросов обращения из базы данных");
//        return questionService.getAllQuestionsByAppealId(id)
//                .stream()
//                .map(Question::getSummary)
//                .collect(Collectors.joining(QUESTIONS_DELIMITER));
//    }

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
}
