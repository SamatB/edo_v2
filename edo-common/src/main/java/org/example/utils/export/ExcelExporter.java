package org.example.utils.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.ZonedDateTime;
import java.util.List;

import static org.example.utils.FileHelper.*;

/**
 * Вспомогательный класс для экспорта в Excel
 */
public abstract class ExcelExporter {
    /**
     * Метод создает новый XLSX файл
     */
    protected XSSFWorkbook createNewWorkBook(String sheetName, List<String> headers) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeaderLine(sheetName, headers, workbook);
        return workbook;
    }

    /**
     * Метод создает новый лист и записывает заголовок в него
     */
    private void writeHeaderLine(String sheetName, List<String> headers, XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet(sheetName);

        Row row = sheet.createRow(0);

        CellStyle style = createCellStyleWithFontHeight(workbook,16, true);

        headers.forEach(cellName -> createCell(sheet, row, headers.indexOf(cellName), cellName, style));
    }

    /**
     * Метод записывает значение в ячейку
     * @param row ряд, в который пишем
     * @param columnCount номер столбца, в который пишем
     * @param value значение для записи
     * @param style стиль шрифта
     */
    protected void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
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

    /**
     * Метод создает стиль шрифта для ячейки
     * @param fontHeight размер шрифта
     * @param isBold жирный шрифт
     * @return стиль ячейки
     */
    protected CellStyle createCellStyleWithFontHeight(XSSFWorkbook workbook, int fontHeight, boolean isBold) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(isBold);
        font.setFontHeight(fontHeight);
        style.setFont(font);
        return style;
    }

    /**
     * Метод записывает данные обращений в XLSX файл
     */
    protected abstract void writeDataLines(List<?> objects, XSSFWorkbook workbook, XSSFSheet sheet);
}
