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
 * Тесты для класса ReportServiceImpl.*
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
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test.csv"));
        Files.deleteIfExists(Paths.get(OUTPUT_DIR + "test.csv.txt.csv"));
    }

    /**
     * Тестирует запись в CSV файл
     * Проверяет существование файла
     */

    @Test
    void writeAppealsToCsv_fileExists() {
        String fileName = OUTPUT_DIR + "test.csv";
        reportService.writeAppealsToCsv(0, 10, fileName);

        assertTrue(Files.exists(Paths.get(fileName)));
    }

    /**
     * Тестирует что название файла заканчивается на .csv
     */
    @Test
    void writeAppealsToCsv_fileHasSuffix() {
        String fileName = OUTPUT_DIR + "test.csv";
        String wrongSuffix = ".txt";
        reportService.writeAppealsToCsv(0, 10,fileName + wrongSuffix);

        assertTrue(Files.exists(Paths.get(fileName + wrongSuffix + ".csv")));
    }
}