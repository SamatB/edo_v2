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
import lombok.extern.log4j.Log4j2;
import org.example.dto.TaskForEmployeeDto;
import org.example.service.FileStorageService;
import org.example.service.TaskForEmployeeService;
import org.example.util.exception.EmptyValueException;
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
@Log4j2
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

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
        //отступ (размер левого поля) слева в переводе на 2,5-3 см
        float marginLeft = 85.0394F;
        //Отступ (размер остальных полей) с остальных сторон в переводе на 1,5 см
        float marginRightTopBottom = 50;
        //Открытие документа
        Document document = new Document(PageSize.A4, marginLeft, marginRightTopBottom, marginRightTopBottom, marginRightTopBottom);
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
            //Ширина блока адресант-адресат
            float addresseeBlockWidth = 40;
            //Блок адресант-адресат
            Table destination = new Table(1);
            destination.setBorder(Rectangle.NO_BORDER);
            destination.setWidth(addresseeBlockWidth);
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

            //Толщина линии
            float lineThickness = 0.3f;
            //Позиция линии по вертикали
            float yDirPosition = 12f;

            Chunk fio = new Chunk("                     (Ф.И.О)                    ");
            fio.setUnderline(lineThickness, yDirPosition);
            destination.addCell(getCell(fio));
            destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
            destination.addCell(getCell(taskForEmployeeDto.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
            Chunk contactDates = new Chunk("        (контактные данные)          ");
            contactDates.setUnderline(lineThickness, yDirPosition);
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
            executorFIO.setUnderline(lineThickness, yDirPosition);
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

            //Позиция факсимиле-изображение по горизонтали над линией подписи
            float xDir = document.getPageSize().getWidth() / 2 + 85;
            //Позиция факсимиле-изображение по вертикали над линией подписи
            float yDir = document.getPageSize().getHeight() / 2 - 230;

            //Факсимиле-изображение
            if (!taskForEmployeeDto.getUuid().isEmpty()) {
                Image facsimile = Image.getInstance(getFacsimileImageFromMinIO());
                facsimile.setAbsolutePosition(xDir, yDir);
                dateAndFacsimile.addCell(getCell("", HorizontalAlignment.CENTER));
                document.add(facsimile);
            } else {
                log.warn("Введите UUID файла");
                throw new EmptyValueException("Не предоставлен ключ UUID файла");
            }

            Chunk dateUnderline = new Chunk("              (дата)              ");
            dateUnderline.setUnderline(lineThickness, yDirPosition);
            dateAndFacsimile.addCell(getCell(dateUnderline));

            Chunk signUnderline = new Chunk("             (подпись)             ");
            signUnderline.setUnderline(lineThickness, yDirPosition);
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