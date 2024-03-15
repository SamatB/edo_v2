package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Appeal;
import org.example.repository.AppealRepository;
import org.example.service.ReportService;
import org.example.utils.AppealExcelExporter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final AppealRepository appealRepository;
    private final AppealExcelExporter appealExcelExporter;

    /**
     * Запись в XLSX файл
     * Если название файла не заканчивается на .xlsx, то добавляется
     *
     * @param fileName название файла, в который будет записан файл
     */
    public void writeAppealsToXlsx(String fileName) {
        log.info("Начало записи в XLSX файл... {}", fileName);
        String suffix = ".xlsx";
        if (!fileName.endsWith(suffix)) {
            log.info("Добавление суффикса {} к имени файла", suffix);
            fileName = fileName + suffix;
        }
        log.info("Получение списка обращений из базы данных");
        List<Appeal> appeals = appealRepository.findAll();

        appealExcelExporter.createNewWorkBook(appeals);

        try {
            log.info("Запись в XLSX файл {}", fileName);
            appealExcelExporter.export(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
