package org.example.service.impl;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfOCG;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.example.dto.TaskForEmployeeDto;

import org.example.service.FileStorageService;
import org.example.service.TaskForEmployeeService;
import org.example.util.exception.EmptyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(TaskForEmployeeServiceImpl.class);
    private String uuid;
    private FileStorageService fileStorageService;


    @Override
    public ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) throws IOException {
        Document document = new Document(PageSize.A4, 85.0394F, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ResponseEntity<Resource> response = fileStorageService.getFile(task.getUuid());
        InputStream inputStream = response.getBody().getInputStream();
        inputStream.readAllBytes();

        this.uuid = task.getUuid();

        PdfWriter.getInstance(document, baos);

        document.open();
        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setColor(Color.BLACK);
            font.setSize(14);

            Table destination = new Table(1);
            destination.setBorder(Rectangle.NO_BORDER);
            destination.setWidth(40);
            destination.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            if (task.getTaskCreatorFirstName().isEmpty()) {
                log.error("Task creator first name is empty");
                throw new EmptyValueException("First name cannot be empty");
            } else {
                destination.addCell(getCell(task.getTaskCreatorFirstName(), HorizontalAlignment.LEFT));
            }
            if (task.getTaskCreatorLastName().isEmpty()) {
                log.error("Task creator last name is empty");
                throw new EmptyValueException("Last name cannot be empty");
            } else {
                destination.addCell(getCell(task.getTaskCreatorLastName(), HorizontalAlignment.LEFT));
            }
            destination.addCell(getCell(task.getTaskCreatorMiddleName(), HorizontalAlignment.LEFT));
            Chunk fio = new Chunk("                     (Ф.И.О)                    ");
            fio.setUnderline(0.3f, 12f);
            destination.addCell(getCell(fio));
            destination.addCell(getCell(task.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
            destination.addCell(getCell(task.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
            Chunk contactDates = new Chunk("        (контактные данные)          ");
            contactDates.setUnderline(0.3f, 12f);
            destination.addCell(getCell(contactDates));
            if (task.getExecutorFirstName().isEmpty()) {
                log.error("Task executor first name is empty");
                throw new EmptyValueException("Task executor's first name cannot be empty");
            } else {
                destination.addCell(getCell(task.getExecutorFirstName(), HorizontalAlignment.LEFT));
            }
            if (task.getExecutorLastName().isEmpty()) {
                log.error("Task executor's last name is empty");
                throw new EmptyValueException("Task executor's last name cannot be empty");
            } else {
                destination.addCell(getCell(task.getExecutorLastName(), HorizontalAlignment.LEFT));
            }
            destination.addCell(getCell(task.getExecutorMiddleName(), HorizontalAlignment.LEFT));
            Chunk executorFIO = new Chunk("         (Ф.И.О исполнителя)          ");
            executorFIO.setUnderline(0.3f, 12f);
            destination.addCell(getCell(executorFIO));
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
            Image facs = Image.getInstance("sign.png");
            facs.scaleAbsolute(100, 80);

            float xDir = document.getPageSize().getWidth() / 2;
            float yDir = document.getPageSize().getHeight() / 2;
            facs.setAbsolutePosition(xDir + 85, yDir - 220);

            if (!task.getUuid().isEmpty()) {
//                dateAndFacsimile.addCell(getCell("", HorizontalAlignment.CENTER));
                dateAndFacsimile.addCell(getCell(getFacsimileImageFromMinIO()));
            } else {
                dateAndFacsimile.addCell(getCell("", HorizontalAlignment.CENTER));
                document.add(facs);
            }

            Chunk dateUnderline = new Chunk("              (дата)              ");
            dateUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(dateUnderline));

            Chunk signUnderline = new Chunk("             (подпись)             ");
            signUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(signUnderline));

            document.add(dateAndFacsimile);
        } catch (DocumentException e) {
            log.info("Error occurred: {0}", e);
        }
        document.close();
        return new ByteArrayResource(baos.toByteArray());
    }

    private java.awt.Image getFacsimileImageFromMinIO() throws IOException {
        Resource resource = fileStorageService.getFile(getFacsimileFileUUID()).getBody();
        return new ImageIcon(resource.getInputStream().readAllBytes()).getImage();
    }

    public String getFacsimileFileUUID() {
        return uuid;
    }

    private static Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static Cell getCell(java.awt.Image image) {
        Cell cell = new Cell();
        cell.add((Element) image);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static Cell getCell(Image image) {
        Cell cell = new Cell();
        cell.add(image);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static Cell getCell(Chunk chunk) {
        Cell cell = new Cell();
        cell.add(new Paragraph(chunk));
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

}
