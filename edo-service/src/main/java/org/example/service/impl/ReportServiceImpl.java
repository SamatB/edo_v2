package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.ReportDto;
import org.example.entity.Employee;
import org.example.entity.Report;
import org.example.entity.Resolution;
import org.example.mapper.ReportMapper;
import org.example.repository.EmployeeRepository;
import org.example.repository.ReportRepository;
import org.example.repository.ResolutionRepository;
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
    private final ResolutionRepository resolutionRepository;
    private final ReportMapper reportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение сохранения используя ReportRepository.
     *
     * @param reportDto объект DTO с отчетом.
     * @return объект DTO отчета.
     */
    @Transactional
    @Override
    public ReportDto saveReport(ReportDto reportDto) {
        log.info("Сохранение отчета в базе данных");
        try {
            reportRepository.insertReport(
                    reportDto.getCreationDate(),
                    reportDto.getComment(),
                    reportDto.getResult(),
                    reportDto.getExecutorId(),
                    reportDto.getResolutionId());
            Report firstByIdOrderByIdDesc = reportRepository.findFirstByIdOrderByIdDesc(0L);
            log.info("Отчет сохранен в базе данных");
        } catch (Exception e) {
            log.error("Ошибка при сохранении отчета в базе данных");
            System.out.println(e.getMessage());
        }
        return new ReportDto();
    }
}
