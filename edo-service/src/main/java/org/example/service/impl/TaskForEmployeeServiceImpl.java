package org.example.service.impl;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.TaskForEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(TaskForEmployeeServiceImpl.class);


    @Override
    public ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) throws IOException {
        log.info("Generating task for employee into pdf" + task.getExecutorFirstName());
        Document document = new Document(PageSize.A4, 85.0394F, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.BLACK);
        font.setSize(14);

        Table destination = new Table(1);
        destination.setBorder(Rectangle.NO_BORDER);
        destination.setWidth(40);
        destination.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        destination.addCell(getCell(task.getTaskCreatorFirstName(), HorizontalAlignment.LEFT));
        destination.addCell(getCell(task.getTaskCreatorLastName(), HorizontalAlignment.LEFT));
        destination.addCell(getCell(task.getTaskCreatorMiddleName(), HorizontalAlignment.LEFT));
        Chunk fio = new Chunk("                    (Ф.И.О)                    ");
        fio.setUnderline(0.3f, 12f);
        fio.setFont(FontFactory.getFont(FontFactory.HELVETICA, 12));
        destination.addCell(getCell(fio, HorizontalAlignment.CENTER));
        destination.addCell(getCell(task.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
        destination.addCell(getCell(task.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
        Chunk contactDates = new Chunk("       (контактные данные)         ");
        contactDates.setUnderline(0.3f, 12f);
        destination.addCell(getCell(contactDates, HorizontalAlignment.CENTER));
        destination.addCell(getCell(task.getExecutorFirstName(), HorizontalAlignment.LEFT));
        destination.addCell(getCell(task.getExecutorLastName(), HorizontalAlignment.LEFT));
        destination.addCell(getCell(task.getExecutorMiddleName(), HorizontalAlignment.LEFT));
        Chunk executorFIO = new Chunk("        (Ф.И.О исполнителя)         ");
        executorFIO.setUnderline(0.3f, 12f);
        destination.addCell(getCell(executorFIO, HorizontalAlignment.CENTER));
        document.add(destination);

        document.add(new Paragraph("\n"));

        Paragraph taskParagraph = new Paragraph("Задание для сотрудника");
        taskParagraph.setFont(font);
        taskParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(taskParagraph);

        document.add(new Paragraph("\n"));

        Paragraph taskDescription = new Paragraph(task.getTaskDescription());
        taskDescription.setFont(font);
        taskDescription.setAlignment(Element.ALIGN_LEFT);
        document.add(taskDescription);
        document.add(new Paragraph("\n\n"));

        Table dateAndFacsimile = new Table(2, 2);
        dateAndFacsimile.setBorder(Rectangle.NO_BORDER);
        dateAndFacsimile.setWidth(100);
        dateAndFacsimile.setHorizontalAlignment(HorizontalAlignment.CENTER);
        dateAndFacsimile.addCell(getCell(task.getTaskCreationDate(), HorizontalAlignment.CENTER));
        dateAndFacsimile.addCell(getCell(String.valueOf(task.getTaskCreatorFacsimileID()), HorizontalAlignment.CENTER));
        Chunk dateUnderline = new Chunk("              (дата)              ");
        dateUnderline.setUnderline(0.3f, 12f);
        dateAndFacsimile.addCell(getCell(dateUnderline, HorizontalAlignment.CENTER));
        Chunk signUnderline = new Chunk("             (подпись)             ");
        signUnderline.setUnderline(0.3f, 12f);
        dateAndFacsimile.addCell(getCell(signUnderline, HorizontalAlignment.CENTER));
        document.add(dateAndFacsimile);

        document.close();
        log.info("Document: {}", document);
        return new ByteArrayResource(baos.toByteArray());
    }

//    @Override
//    public void generateTaskForEmployeeIntoPDF(HttpServletResponse response, TaskForEmployeeDto task) throws IOException {
//        log.info("Generating task for employee into pdf" + task.getExecutorFirstName());
//        Document document = new Document(PageSize.A4, 85.0394F, 50, 50, 50);
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//
//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
//        font.setColor(Color.BLACK);
//        font.setSize(14);
//
//        Table destination = new Table(1);
//        destination.setBorder(Rectangle.NO_BORDER);
//        destination.setWidth(40);
//        destination.setHorizontalAlignment(HorizontalAlignment.RIGHT);
//        destination.addCell(getCell(task.getTaskCreatorFirstName(), HorizontalAlignment.LEFT));
//        destination.addCell(getCell(task.getTaskCreatorLastName(), HorizontalAlignment.LEFT));
//        destination.addCell(getCell(task.getTaskCreatorMiddleName(), HorizontalAlignment.LEFT));
//        Chunk fio = new Chunk("                    (Ф.И.О)                    ");
//        fio.setUnderline(0.3f, 12f);
//        fio.setFont(FontFactory.getFont(FontFactory.HELVETICA, 12));
//        destination.addCell(getCell(fio, HorizontalAlignment.CENTER));
//        destination.addCell(getCell(task.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
//        destination.addCell(getCell(task.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
//        Chunk contactDates = new Chunk("       (контактные данные)         ");
//        contactDates.setUnderline(0.3f, 12f);
//        destination.addCell(getCell(contactDates, HorizontalAlignment.CENTER));
//        destination.addCell(getCell(task.getExecutorFirstName(), HorizontalAlignment.LEFT));
//        destination.addCell(getCell(task.getExecutorLastName(), HorizontalAlignment.LEFT));
//        destination.addCell(getCell(task.getExecutorMiddleName(), HorizontalAlignment.LEFT));
//        Chunk executorFIO = new Chunk("        (Ф.И.О исполнителя)         ");
//        executorFIO.setUnderline(0.3f, 12f);
//        destination.addCell(getCell(executorFIO, HorizontalAlignment.CENTER));
//        document.add(destination);
//
//        document.add(new Paragraph("\n"));
//
//        Paragraph taskParagraph = new Paragraph("Задание для сотрудника");
//        taskParagraph.setFont(font);
//        taskParagraph.setAlignment(Element.ALIGN_CENTER);
//        document.add(taskParagraph);
//
//        document.add(new Paragraph("\n"));
//
//        Paragraph taskDescription = new Paragraph(task.getTaskDescription());
//        taskDescription.setFont(font);
//        taskDescription.setAlignment(Element.ALIGN_LEFT);
//        document.add(taskDescription);
//        document.add(new Paragraph("\n\n"));
//
//        Table dateAndFacsimile = new Table(2, 2);
//        dateAndFacsimile.setBorder(Rectangle.NO_BORDER);
//        dateAndFacsimile.setWidth(100);
//        dateAndFacsimile.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        dateAndFacsimile.addCell(getCell(task.getTaskCreationDate(), HorizontalAlignment.CENTER));
//        dateAndFacsimile.addCell(getCell(String.valueOf(task.getTaskCreatorFacsimileID()), HorizontalAlignment.CENTER));
//        Chunk dateUnderline = new Chunk("              (дата)              ");
//        dateUnderline.setUnderline(0.3f, 12f);
//        dateAndFacsimile.addCell(getCell(dateUnderline, HorizontalAlignment.CENTER));
//        Chunk signUnderline = new Chunk("             (подпись)             ");
//        signUnderline.setUnderline(0.3f, 12f);
//        dateAndFacsimile.addCell(getCell(signUnderline, HorizontalAlignment.CENTER));
//        document.add(dateAndFacsimile);
//
//        document.close();
//        log.info("Document: {}", document);
//
//    }

    private static Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static Cell getCell(Chunk chunk, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(chunk));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
