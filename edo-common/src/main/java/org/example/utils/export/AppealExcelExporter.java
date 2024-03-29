package org.example.utils.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dto.AppealDto;
import org.example.dto.QuestionDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.example.utils.FileHelper.*;

/**
 * Вспомогательный класс для экспорта обращений в Excel
 */

@Slf4j
public class AppealExcelExporter extends ExcelExporter {

    private XSSFWorkbook createAppealReport(List<AppealDto> appealDtos) {
        XSSFWorkbook workbook = createNewWorkBook(APPEAL_SHEET_NAME_RUS, APPEAL_REPORT_COLUMNS_RUS);
        writeDataLines(appealDtos, workbook, workbook.getSheet(APPEAL_SHEET_NAME_RUS));
        return workbook;
    }


    /**
     * Метод проверяет, что переданные объекты списка являются обращениями
     * и передает эти обращения в метод, который записывает данные обращений в XLSX файл
     *
     * @param objects список обращений
     * @param sheet XLSX лист
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void writeDataLines(List<?> objects, XSSFWorkbook workbook, XSSFSheet sheet) {
        if (objects != null
                && !objects.isEmpty()
                && objects.get(0) instanceof AppealDto) {
            writeAppealDataLines((List<AppealDto>) objects, workbook, sheet);
        } else {
            log.error("Переданные данные не являются обращениями");
        }
    }

    /**
     * Метод записывает данные обращений в XLSX файл
     */
    private void writeAppealDataLines(List<AppealDto> appeals, XSSFWorkbook workbook, XSSFSheet sheet) {
        AtomicInteger rowCount = new AtomicInteger(1);

        CellStyle style = createCellStyleWithFontHeight(workbook,14, false, HorizontalAlignment.LEFT);

        appeals.forEach(appeal -> {
            Row row = sheet.createRow(rowCount.getAndIncrement());
            int columnCount = 0;
            log.info("Запись обращения {} в XLSX файл", appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId());
            createCell(sheet,row, columnCount++, (appeal.getNumber() != null
                    ? appeal.getNumber()
                    : appeal.getId()), style);
            createCell(sheet, row, columnCount++, appeal.getCreationDate(), style);
            createCell(sheet, row, columnCount++, appeal.getCreator().getFioNominative(), style);
            createCell(sheet, row, columnCount++, appeal.getQuestions().stream()
                    .map(QuestionDto::getSummary)
                    .collect(Collectors.joining(COMMA_DELIMITER)), style);
            createCell(sheet, row, columnCount, (appeal.getStatusType() != null
                    ? appeal.getStatusType().getRusStatusType()
                    : "Статус недоступен"), style);
        });
    }

    /**
     * Метод записывает данные обращений в XLSX файл на диске
     *
     * @param fileName имя XLSX файла
     * @throws IOException при ошибке записи на диск
     */
    public void exportAppealsToXlsx(String fileName, List<AppealDto> appeals) throws IOException {
        XSSFWorkbook workbook = createAppealReport(appeals);
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            log.error("Ошибка записи в XLSX файл", e);
        }
    }

    /**
     * Метод записывает данные обращений в XLSX файл в поток байтов для дальнейшего использования
     *
     * @return поток байтов с файлом в формате XLSX
     * @throws IOException при ошибке записи в поток
     */
    public ByteArrayInputStream exportAppealsToXlsx(List<AppealDto> appeals) throws IOException {
        XSSFWorkbook workbook = createAppealReport(appeals);
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
