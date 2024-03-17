package org.example.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ReportServiceImpl.
 *
 * Работают только при установке FetchType.EAGER на поле appeal.creator
 * Иначе падает окружение с hibernate no session
 */
@SpringBootTest
class ReportServiceImplTest {
    @Autowired
    private ReportServiceImpl reportService;

    private static final String OUTPUT_DIR = "src/test/resources/";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test.xlsx"));
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test.xlsx.txt.xlsx"));
    }

    /**
     * Тестирует запись в XLSX файл
     * Проверяет существование файла
     */
    @Test
    void writeAppealsToXlsx_fileExists() {
        String fileName = OUTPUT_DIR + "test.xlsx";
        reportService.writeAppealsToXlsx(fileName);

        assertTrue(Files.exists(Paths.get(fileName)));
    }

    /**
     * Тестирует что название файла заканчивается на .xlsx
     */
    @Test
    void writeAppealsToXlsx_fileHasSuffix() {
        String fileName = OUTPUT_DIR + "test.xlsx";
        String wrongSuffix = ".txt";
        reportService.writeAppealsToXlsx(fileName + wrongSuffix);

        assertTrue(Files.exists(Paths.get(fileName + wrongSuffix + ".xlsx")));
    }

}