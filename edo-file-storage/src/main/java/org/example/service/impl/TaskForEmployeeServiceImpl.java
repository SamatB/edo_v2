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
import com.lowagie.text.pdf.PdfWriter;
import org.example.dto.TaskForEmployeeDto;

import org.example.service.FileStorageService;
import org.example.service.TaskForEmployeeService;
import org.example.util.exception.EmptyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(TaskForEmployeeServiceImpl.class);
    private String uuid;
    private final FileStorageService fileStorageService;

    @Autowired
    public TaskForEmployeeServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Данный метод принимает на вход TaskForEmployeeDto,
     * создает документ формата PDF размера А4,
     * расставляет указанные поля
     * в теле ответа отправляется созданный PDF файл формата А4
     *
     * @param taskForEmployeeDto - входящие данные.
     * @return - ответ, который содержит созданный PDF файл в виде ByteArrayResource
     */
    @Override
    public ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto taskForEmployeeDto) {
        //Открытие документа
        Document document = new Document(PageSize.A4, 85.0394F, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //входящий UUID передается в переменную класса, чтобы его далее можно было использовать в методе getFacsimileFileUUID()
        this.uuid = taskForEmployeeDto.getUuid();

        //Образование документа в PDF
        PdfWriter.getInstance(document, baos);
        document.open();

        try {
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setColor(Color.BLACK);
            font.setSize(14);

            //Блок адресант-адресат
            Table destination = new Table(1);
            destination.setBorder(Rectangle.NO_BORDER);
            destination.setWidth(40);
            destination.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            if (taskForEmployeeDto.getTaskCreatorFirstName().isEmpty()) {
                log.warn("Имя создающего задания лица не может быть пустым");
                throw new EmptyValueException("Не указано имя создающего задания лица");
            } else {
                destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorFirstName(), HorizontalAlignment.LEFT));
            }
            if (taskForEmployeeDto.getTaskCreatorLastName().isEmpty()) {
                log.warn("Фамилия создающего задания лица не может быть пустой");
                throw new EmptyValueException("Не указана фамилия создающего задания лица");
            } else {
                destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorLastName(), HorizontalAlignment.LEFT));
            }
            destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorMiddleName(), HorizontalAlignment.LEFT));
            Chunk fio = new Chunk("                     (Ф.И.О)                    ");
            fio.setUnderline(0.3f, 12f);
            destination.addCell(getCell(fio));
            destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
            destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
            Chunk contactDates = new Chunk("        (контактные данные)          ");
            contactDates.setUnderline(0.3f, 12f);
            destination.addCell(getCell(contactDates));
            if (taskForEmployeeDto.getExecutorFirstName().isEmpty()) {
                log.warn("Имя исполнителя задания не может быть пустым");
                throw new EmptyValueException("Не указано имя исполнителя задания");
            } else {
                destination.addCell(getCell(taskForEmployeeDto.getExecutorFirstName(), HorizontalAlignment.LEFT));
            }
            if (taskForEmployeeDto.getExecutorLastName().isEmpty()) {
                log.warn("Фамилия исполнителя задания не может быть пустой");
                throw new EmptyValueException("Не указана фамилия исполнителя задания");
            } else {
                destination.addCell(getCell(taskForEmployeeDto.getExecutorLastName(), HorizontalAlignment.LEFT));
            }
            destination.addCell(getCell(taskForEmployeeDto.getExecutorMiddleName(), HorizontalAlignment.LEFT));
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

            //Блок описания (текста) задания
            Paragraph taskDescription = new Paragraph(taskForEmployeeDto.getTaskDescription());
            taskDescription.setFont(font);
            taskDescription.setAlignment(Element.ALIGN_LEFT);
            document.add(taskDescription);
            document.add(new Paragraph("\n\n"));

            //Блок (дата) и (подпись)
            Table dateAndFacsimile = new Table(2, 2);
            dateAndFacsimile.setBorder(Rectangle.NO_BORDER);
            dateAndFacsimile.setWidth(100);
            dateAndFacsimile.setHorizontalAlignment(HorizontalAlignment.CENTER);
            dateAndFacsimile.addCell(getCell(taskForEmployeeDto.getTaskCreationDate(), HorizontalAlignment.CENTER));

            float xDir = document.getPageSize().getWidth() / 2;
            float yDir = document.getPageSize().getHeight() / 2;

            //Факсимиле-изображение
            Image facsimile = Image.getInstance(getFacsimileImageFromMinIO());
            facsimile.setAbsolutePosition(xDir + 85, yDir - 230);
            if (!taskForEmployeeDto.getUuid().isEmpty()) {
                dateAndFacsimile.addCell(getCell("", HorizontalAlignment.CENTER));
                document.add(facsimile);
            } else {
                log.warn("Введите UUID файла");
                throw new EmptyValueException("Не предоставлен ключ UUID файла");
            }

            Chunk dateUnderline = new Chunk("              (дата)              ");
            dateUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(dateUnderline));

            Chunk signUnderline = new Chunk("             (подпись)             ");
            signUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(signUnderline));

            document.add(dateAndFacsimile);
        } catch (DocumentException | IOException e) {
            log.warn("Ошибка в : {0}", e);
        }
        //Закрытие документа - формирование pdf
        document.close();
        return new ByteArrayResource(baos.toByteArray());
    }

    private Image getFacsimileImageFromMinIO() throws IOException {
        Resource resource = null;
        if (fileStorageService.getFile(getFacsimileFileUUID()).hasBody()) {
            resource = fileStorageService.getFile(getFacsimileFileUUID()).getBody();
        }
        assert resource != null;
        return Image.getInstance(resource.getInputStream().readAllBytes());
    }

    /**
     * Метод возвращает переданный UUID.
     */
    public String getFacsimileFileUUID() {
        return uuid;
    }

    /**
     * Метот для формирования строк блока адресант-адресат.
     */
    private static Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    /**
     * Метот для формирования элементов блока (дата) и (подпись).
     */
    private static Cell getCell(Chunk chunk) {
        Cell cell = new Cell();
        cell.add(new Paragraph(chunk));
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}