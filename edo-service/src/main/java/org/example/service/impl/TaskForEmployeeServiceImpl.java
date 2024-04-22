package org.example.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import org.example.dto.TaskForEmployeeDto;
import org.example.repository.FacsimileRepository;
import org.example.repository.TaskForEmployeeRepository;
import org.example.service.TaskForEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
            list.add(new ListItem("От кого задание"));
            list.add(new ListItem(task.getTaskCreatorFirstName()));
            list.add(new ListItem(task.getTaskCreatorLastName()));
            if (task.getTaskCreatorMiddleName() != null) {
                list.add(new ListItem(task.getTaskCreatorMiddleName()));
            }
            list.add(new Chunk("(Ф.И.О)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
            if (task.getTaskCreatorEmail() != null) {
                list.add(new ListItem(task.getTaskCreatorEmail()));
            }
            if (task.getTaskCreatorPhoneNumber() != null) {
                list.add(new ListItem(task.getTaskCreatorPhoneNumber()));
            }
            list.add(new ListItem("(контактные данные)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));
            list.add(new ListItem(task.getExecutorFirstName()));
            list.add(new ListItem(task.getExecutorLastName()));
            if (task.getExecutorMiddleName() != null) {
                list.add(new ListItem(task.getExecutorMiddleName()));
            }
            list.add(new ListItem("(Ф.И.О исполнителя)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL)));


            Paragraph taskForEmployee = new Paragraph("Задание для сотрудника");
            taskForEmployee.setFont(font);
            taskForEmployee.setAlignment(Element.ALIGN_CENTER);

            Paragraph taskDescripion = new Paragraph(task.getTaskDescription());
            taskDescripion.setFont(font);
            taskDescripion.setAlignment(Element.ALIGN_CENTER);

            document.add(list);
            document.add(taskForEmployee);
            document.add(taskDescripion);
            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("TaskForEmployeeGenerate error " + e.getMessage());
        }
        return null;
    }
}
