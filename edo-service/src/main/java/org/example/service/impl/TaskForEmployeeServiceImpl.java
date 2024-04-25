package org.example.service.impl;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.TaskForEmployeeDto;
import org.example.entity.Facsimile;
import org.example.entity.TaskForEmployee;
import org.example.mapper.TaskForEmployeeMapper;
import org.example.repository.FacsimileRepository;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private final TaskForEmployeeMapper taskForEmployeeMapper;
    private final FacsimileRepository facsimileRepository;

    @Autowired
    public TaskForEmployeeServiceImpl(TaskForEmployeeMapper taskForEmployeeMapper, FacsimileRepository facsimileRepository) {
        this.taskForEmployeeMapper = taskForEmployeeMapper;
        this.facsimileRepository = facsimileRepository;
    }

    @Override
    public void generateTaskForEmployeeIntoPDF(HttpServletResponse response, TaskForEmployeeDto task) throws IOException {

        TaskForEmployee taskForEmployee = taskForEmployeeMapper.dtoToEntity(task);

        Document document = new Document(PageSize.A4, 85.0394F, 42.5197F, 42.5197F, 42.5197F);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(14);
        font.setColor(Color.BLACK);

        List list = new List(true, 14);
        list.add(new ListItem(taskForEmployee.getTaskCreatorFirstName()));
        list.add(new ListItem(taskForEmployee.getTaskCreatorLastName()));
        if (task.getTaskCreatorMiddleName() != null) {
            list.add(new ListItem(taskForEmployee.getTaskCreatorMiddleName()));
        }
        list.add(new Chunk("(Ф.И.О)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));

        document.add(new Paragraph("\n"));

        if (taskForEmployee.getTaskCreatorEmail() != null) {
            list.add(new ListItem(taskForEmployee.getTaskCreatorEmail()));
        }
        if (task.getTaskCreatorPhoneNumber() != null) {
            list.add(new ListItem(taskForEmployee.getTaskCreatorPhoneNumber()));
        }
        list.add(new ListItem("(контактные данные)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));

        document.add(new Paragraph("\n"));

        list.add(new ListItem(taskForEmployee.getExecutorFirstName()));
        list.add(new ListItem(taskForEmployee.getExecutorLastName()));
        if (taskForEmployee.getExecutorMiddleName() != null) {
            list.add(new ListItem(task.getExecutorMiddleName()));
        }
        list.add(new ListItem("(Ф.И.О исполнителя)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
        document.add(new Paragraph("\n"));
        Paragraph paragraph = new Paragraph();
        paragraph.add(list);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

        Paragraph taskParagraph = new Paragraph("Задание для сотрудника");
        taskParagraph.setFont(font);
        taskParagraph.setAlignment(Element.ALIGN_CENTER);

        Paragraph taskDescription = new Paragraph(taskForEmployee.getTaskDescription());
        taskDescription.setFont(font);
        taskDescription.setAlignment(Element.ALIGN_LEFT);

        Table table = new Table(2);
        Paragraph date = new Paragraph(taskForEmployee.getTaskCreationDate().toString());
        date.setFont(font);
        date.setAlignment(Element.ALIGN_LEFT);
        document.add(date);
        table.addCell(getCell("(дата)", HorizontalAlignment.LEFT));
        if (task.getTaskCreatorFacsimileId() != null) {
            Optional<Facsimile> facsimile = facsimileRepository.findById(taskForEmployee.getTaskCreatorFacsimileId());
            Paragraph facs = new Paragraph(String.valueOf(facsimile));
            facs.setFont(font);
            facs.setAlignment(Element.ALIGN_RIGHT);
            document.add(facs);
        } else {
            taskForEmployee.setTaskCreatorFacsimileId(null);
        }

        table.addCell(getCell("(подпись)", HorizontalAlignment.RIGHT));
        document.add(table);
        document.add(taskParagraph);
        document.add(taskDescription);
        document.close();
    }

    private static Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
