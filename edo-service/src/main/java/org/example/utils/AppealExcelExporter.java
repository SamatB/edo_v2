package org.example.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для экспорта обращений в Excel
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class AppealExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Appeal> appeals;
    private QuestionService questionService;

    @Autowired
    public AppealExcelExporter(QuestionService questionService) {
        this.questionService = questionService;
    }

    public void createNewWorkBook(List<Appeal> listAppeals) {
        workbook = new XSSFWorkbook();
        this.appeals = listAppeals;
    }
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Обращения");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        String[] cellNames = {"Номер обращения", "Дата создания", "ФИО создателя", "Список вопросов обращения", "Статус обращения"};
        for (int i = 0; i < cellNames.length; i++) {
            createCell(row, i, cellNames[i], style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof ZonedDateTime) {
            cell.setCellValue(((ZonedDateTime) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Appeal appeal : appeals) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            log.info("Получение списка вопросов обращения из базы данных");
            String questions = questionService.getAllQuestionsByAppealId(appeal.getId())
                    .stream()
                    .map(Question::getSummary)
                    .collect(Collectors.joining(", "));

            log.info("Запись обращения {} в XLSX файл", appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId());

            createCell(row, columnCount++, (appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId()), style);
            createCell(row, columnCount++, appeal.getCreationDate(), style);
            createCell(row, columnCount++, appeal.getCreator().getFioNominative(), style);
            createCell(row, columnCount++, questions, style);
            createCell(row, columnCount, appeal.getStatusType().getRusStatusType(), style);
        }
    }

    public void export(String fileName) throws IOException {
        writeHeaderLine();
        writeDataLines();
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            log.error("Ошибка записи в XLSX файл", e);
        }
    }

    public ByteArrayInputStream exportAppealsToXlsx() throws IOException {
        writeHeaderLine();
        writeDataLines();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Ошибка записи в XLSX файл", e);
            return null;
        }
    }
}
