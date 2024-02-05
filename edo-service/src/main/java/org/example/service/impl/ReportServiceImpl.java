package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReportDto;
import org.example.dto.ResolutionDto;
import org.example.entity.Report;
import org.example.entity.Resolution;
import org.example.mapper.ReportMapper;
import org.example.repository.ReportRepository;
import org.example.repository.ResolutionRepository;
import org.example.service.ReportService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ResolutionRepository resolutionRepository;
    private final ReportMapper reportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null или резолюция в отчете равна null, то выбрасывается IllegalArgumentException.
     * Метод выполняет сохранение используя ReportRepository.
     *
     * @param reportDto объект DTO отчета c id = 0.
     * @return объект DTO отчета с присвоенным id (значения ленивых полей - null)
     */
    @Transactional
    @Override
    public ReportDto saveReport(ReportDto reportDto) {
        log.info("Сохранение отчета в базе данных");
        ResolutionDto resolutionDto;
        try {
            resolutionDto = reportDto.getResolution();
        } catch (NullPointerException e) {
            final String message = "Отчет не сохранен в БД - отчет не должен быть null";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        Long resolutionId;
        try {
            resolutionId = resolutionDto.getId();
        } catch (NullPointerException e) {
            final String message = "Отчет не сохранен в БД - ссылка на резолюцию не должна быть null";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        Resolution resolution = resolutionRepository.findById(resolutionId).get();
        Report report = reportMapper.dtoToEntity(reportDto);
        Report savedReport = reportRepository.save(report);
        resolution.getReports().add(savedReport);
        resolutionRepository.save(resolution);
        ReportDto reportDtoToReturn = reportMapper.entityToDto(savedReport);
        log.info("Отчет сохранен в базе данных");
        return reportDtoToReturn;
    }
}
