package org.example.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.lowagie.text.pdf.PdfOCG;
import com.lowagie.text.pdf.PdfWriter;
import org.example.dto.TaskForEmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.FacsimileMapper;
import org.example.repository.EmployeeRepository;
import org.example.repository.FacsimileRepository;
import org.example.service.TaskForEmployeeService;
import org.example.utils.exception.EmptyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.Principal;
import java.util.Base64;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(TaskForEmployeeServiceImpl.class);
    private final FacsimileMapper facsimileMapper;
    private final FacsimileRepository facsimileRepository;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public TaskForEmployeeServiceImpl(FacsimileMapper facsimileMapper, FacsimileRepository facsimileRepository, EmployeeRepository employeeRepository) {
        this.facsimileMapper = facsimileMapper;
        this.facsimileRepository = facsimileRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task, String authToken) throws IOException {

        log.info("Meder {}", extractUsername(authToken));
        System.out.println(employeeRepository.findByExternalId(extractUsername(authToken)));
        Employee employee = employeeRepository.findByExternalId(extractUsername(authToken));

        Document document = new Document(PageSize.A4, 85.0394F, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

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
                destination.addCell(getCell(task.setTaskCreatorFirstName(employee.getFirstName()), HorizontalAlignment.LEFT));
            }
            if (task.getTaskCreatorLastName().isEmpty()) {
                log.error("Task creator last name is empty");
                throw new EmptyValueException("Last name cannot be empty");
            } else {
                destination.addCell(getCell(task.getTaskCreatorLastName(), HorizontalAlignment.LEFT));
            }
            destination.addCell(getCell(task.getTaskCreatorMiddleName(), HorizontalAlignment.LEFT));
            Chunk fio = new Chunk("                   (Ф.И.О)                   ");
            fio.setUnderline(0.3f, 14f);
            fio.setFont(FontFactory.getFont(FontFactory.HELVETICA, 12));
            destination.addCell(getCell(fio, HorizontalAlignment.CENTER));
            destination.addCell(getCell(task.getTaskCreatorEmail(), HorizontalAlignment.LEFT));
            destination.addCell(getCell(task.getTaskCreatorPhoneNumber(), HorizontalAlignment.LEFT));
            Chunk contactDates = new Chunk("       (контактные данные)         ");
            contactDates.setUnderline(0.3f, 12f);
            destination.addCell(getCell(contactDates, HorizontalAlignment.CENTER));
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
            Image facs = Image.getInstance("img.png");
            facs.scaleAbsolute(100, 80);

            dateAndFacsimile.addCell(getCell(facs, HorizontalAlignment.CENTER));

            Chunk dateUnderline = new Chunk("              (дата)              ");
            dateUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(dateUnderline, HorizontalAlignment.CENTER));

            Chunk signUnderline = new Chunk("             (подпись)             ");
            signUnderline.setUnderline(0.3f, 12f);
            dateAndFacsimile.addCell(getCell(signUnderline, HorizontalAlignment.CENTER));

            document.add(dateAndFacsimile);
        } catch (DocumentException e) {
            log.info("Error occurred: {0}", e);
        }
        document.close();
        return new ByteArrayResource(baos.toByteArray());
    }

    private static Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    private static Cell getCell(Image image, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(image);
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

    public static String extractUsername(String token) {
        // Разделить токен на три части по точкам
        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        // Декодировать и десериализовать вторую часть (Payload) из Base64 в JSON
        String payload = new String(Base64.getDecoder().decode(parts[1]));

        // Извлечь имя пользователя из JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode payloadJson = mapper.readTree(payload);
            return payloadJson.has("sub") ? payloadJson.get("sub").asText() : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract username from JWT token", e);
        }
    }

//    private static String extractUsername(String token) {
//        // JWT токен состоит из трех частей, разделенных точками
//        String[] parts = token.split("\\.");
//
//        // Проверяем, что у нас есть три части
//        if (parts.length != 3) {
//            throw new IllegalArgumentException("Invalid JWT token format");
//        }
//
//        // Декодируем и извлекаем полезную нагрузку (Payload), которая находится во второй части токена
//        String payload = parts[1];
//        byte[] decodedBytes = Base64.getDecoder().decode(payload);
//        String decodedPayload = new String(decodedBytes);
//
//        // Преобразуем полезную нагрузку из JSON в объект и извлекаем имя пользователя (или другую нужную информацию)
//
//        return extractUsernameFromJson(decodedPayload);
//    }
//
//    private static String extractUsernameFromJson(String payloadJson) {
//        // В этом примере предполагается, что полезная нагрузка (Payload) в формате JSON имеет поле "sub" с именем пользователя
//        // Здесь можно использовать любую библиотеку для работы с JSON, например, org.json.JSONObject
//        // Для простоты мы используем простую обработку строк
//
//        // Удаляем кавычки из JSON строки
//        String cleanJson = payloadJson.replaceAll("\"", "");
//
//        // Ищем значение поля "sub" (предполагается, что это имя пользователя)
//        int subIndex = cleanJson.indexOf("sub:");
//        if (subIndex != -1) {
//            // Находим конец значения
//            int endIndex = cleanJson.indexOf(",", subIndex);
//            if (endIndex == -1) {
//                endIndex = cleanJson.length();
//            }
//
//            // Извлекаем значение поля "sub"
//            String subFieldValue = cleanJson.substring(subIndex + 4, endIndex);
//            return subFieldValue.trim();
//        }
//
//        return null; // Возвращаем null, если не удалось извлечь имя пользователя
//    }

}
