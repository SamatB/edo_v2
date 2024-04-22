package org.example.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xddf.usermodel.text.TextAlignment;
import org.example.dto.TaskForEmployeeDto;
import org.example.entity.Facsimile;
import org.example.repository.FacsimileRepository;
import org.example.repository.TaskForEmployeeRepository;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

/**
 * Сервис для формирования PDF файла заполненного бланка задания для сотрудника.
 */
@Service
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    @Autowired
    private TaskForEmployeeRepository taskForEmployeeRepository;

    @Autowired
    private FacsimileRepository facsimileRepository;

    @Override
    public TaskForEmployeeDto generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) {
        Document document = new Document(PageSize.A4, 85.0394F, 42.5197F, 42.5197F, 42.5197F);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("zadanie-dlya-sotrudnika.pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setSize(14);
            font.setColor(Color.BLACK);

            List list = new List(true, 14);
            list.add(new ListItem(task.getTaskCreatorFirstName()));
            list.add(new ListItem(task.getTaskCreatorLastName()));
            if (task.getTaskCreatorMiddleName() != null) {
                list.add(new ListItem(task.getTaskCreatorMiddleName()));
            }
            list.add(new Chunk("(Ф.И.О)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
            document.add(new Paragraph("\n"));
            if (task.getTaskCreatorEmail() != null) {
                list.add(new ListItem(task.getTaskCreatorEmail()));
            }
            if (task.getTaskCreatorPhoneNumber() != null) {
                list.add(new ListItem(task.getTaskCreatorPhoneNumber()));
            }
            list.add(new ListItem("(контактные данные)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
            document.add(new Paragraph("\n"));
            list.add(new ListItem(task.getExecutorFirstName()));
            list.add(new ListItem(task.getExecutorLastName()));
            if (task.getExecutorMiddleName() != null) {
                list.add(new ListItem(task.getExecutorMiddleName()));
            }
            list.add(new ListItem("(Ф.И.О исполнителя)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
            document.add(new Paragraph("\n"));
            Paragraph paragraph = new Paragraph();
            paragraph.add(list);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);

            Paragraph taskForEmployee = new Paragraph("Задание для сотрудника");
            taskForEmployee.setFont(font);
            taskForEmployee.setAlignment(Element.ALIGN_CENTER);

            Paragraph taskDescription = new Paragraph(task.getTaskDescription());
            taskDescription.setFont(font);
            taskDescription.setAlignment(Element.ALIGN_LEFT);

            Table table = new Table(2);
            Paragraph date = new Paragraph(String.valueOf(task.getTaskCreationDate()));
            date.setFont(font);
            date.setAlignment(Element.ALIGN_LEFT);
            document.add(date);
            table.addCell(getCell("(дата)", HorizontalAlignment.LEFT));
            if (task.getTaskCreatorFacsimile() != null) {
                Optional<Facsimile> facsimile = facsimileRepository.findById(task.getTaskCreatorFacsimile().getId());
                Paragraph facs = new Paragraph(String.valueOf(facsimile));
                facs.setFont(font);
                facs.setAlignment(Element.ALIGN_RIGHT);
                document.add(facs);
            }

            table.addCell(getCell("(подпись)", HorizontalAlignment.RIGHT));
            document.add(table);
            document.add(taskForEmployee);
            document.add(taskDescription);
            document.close();


        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("TaskForEmployeeGenerate error " + e.getMessage());
        }
        return null;
    }

    public Cell getCell(String text, HorizontalAlignment alignment) {
        Cell cell = new Cell();
        cell.add(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
