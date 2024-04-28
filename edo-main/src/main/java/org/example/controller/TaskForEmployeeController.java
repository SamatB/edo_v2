package org.example.controller;

import com.lowagie.text.*;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskForEmployeeDto;
import org.example.feign.TaskForEmployeeClient;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/task-for-employee")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task for employee")
public class TaskForEmployeeController {

    @Autowired
    private final TaskForEmployeeClient taskForEmployeeClient;
    private final FileService fileService;

//    @PostMapping
//    public void generateTaskForEmployeeIntoPDF(HttpServletResponse response, @RequestBody TaskForEmployeeDto taskForEmployeeDto) throws IOException {
//        response.setContentType("application/pdf");
//        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//        String currentDate = dateTime.format(new Date());
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=taskForEmployeeDto" + currentDate + ".pdf";
//        response.setHeader(headerKey, headerValue);
//        log.info("Созданный PDF файл задания по резалюции {}", taskForEmployeeDto);
//        taskForEmployeeClient.convertTaskForEmployeeIntoPDF(response, taskForEmployeeDto);
//    }

    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> createTaskForEmployee(@RequestBody TaskForEmployeeDto taskForEmployeeDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDate = dateTime.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=taskForEmployeeDto" + currentDate + ".pdf";
        headers.add(headerKey, headerValue);
        ByteArrayResource bis = taskForEmployeeClient.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);

        return ResponseEntity
                .ok()
                .header(String.valueOf(headers))
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis);
    }

}
