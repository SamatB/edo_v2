package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dto.AppealDto;
import org.example.dto.QuestionDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.FileHelper.*;

/**
 * Вспомогательный класс для экспорта обращений в Excel
 */

@Slf4j
public class AppealExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<AppealDto> appeals;

    public void createNewWorkBook(List<AppealDto> listAppeals) {
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

        String[] cellNames = APPEAL_REPORT_COLUMNS.toArray(new String[0]);

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
            cell.setCellValue(((ZonedDateTime) value).format(DAY_FIRST_DATE_NO_TIME_FORMATTER));
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

        for (AppealDto appeal : appeals) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            log.info("Запись обращения {} в XLSX файл", appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId());

            createCell(row, columnCount++, (appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId()), style);
            createCell(row, columnCount++, appeal.getCreationDate(), style);
            createCell(row, columnCount++, appeal.getCreator().getFioNominative(), style);
            createCell(row, columnCount++, appeal.getQuestions().stream()
                    .map(QuestionDto::getSummary)
                    .collect(Collectors.joining(COMMA_DELIMITER)), style);
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
