package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.ReportDto;
import org.example.entity.Employee;
import org.example.entity.Report;
import org.example.mapper.ReportMapper;
import org.example.repository.EmployeeRepository;
import org.example.repository.ReportRepository;
import org.example.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final EmployeeRepository employeeRepository;
//    private final ReportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение сохранения используя ReportRepository.
     *
     * @param reportDto объект DTO с отчетом.
     * @return объект DTO отчета.
     */
    @Override
    public ReportDto saveReport(ReportDto reportDto) {

        Report report = new Report();
        report.setComment("Комментарий 100");
        report.setCreationDate(ZonedDateTime.now());
        Employee executor = new Employee("An", "Nn", null, null, "email1",
                null, null, null, null, null, null, null,
                null, null, null, ZonedDateTime.now(), null, null);
        report.setExecutor(executor);
        report.setResult(true);

        Optional<Report> byId = reportRepository.findById(1L);
        System.out.println();
        Report report1 = reportRepository.save(report);
        return new ReportDto();
    }
}
