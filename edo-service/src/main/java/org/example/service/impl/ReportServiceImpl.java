package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReportDto;
import org.example.entity.BaseEntity;
import org.example.entity.Report;
import org.example.entity.Resolution;
import org.example.mapper.ReportMapper;
import org.example.repository.EmployeeRepository;
import org.example.repository.ReportRepository;
import org.example.repository.ResolutionRepository;
import org.example.service.ReportService;
import org.example.service.ResolutionService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final EmployeeRepository employeeRepository;
    private final ResolutionService resolutionService;
    private final ResolutionRepository resolutionRepository;
    private final ReportMapper reportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение сохранения используя ReportRepository.
     *
     * @param reportDto объект DTO отчета c id = 0.
     * @return объект DTO отчета с присвоенным id.
     */
    @Transactional
    @Override
    public ReportDto saveReport(ReportDto reportDto) {
        log.info("Сохранение отчета в базе данных");
        try {
            Long resolutionId = reportDto.getResolution().getId();
            Optional<Resolution> optionalResolution = resolutionRepository.findById(resolutionId);
            Resolution resolution = optionalResolution.get();
//            Long creatorId = resolution.getCreator().getId();
//            Long curatorId = resolution.getCurator().getId();
//            Long signerId = resolution.getSigner().getId();
            Report report = reportMapper.dtoToEntity(reportDto);
            Report savedReport = reportRepository.save(report);
            resolution.getReports().add(savedReport);
            resolutionRepository.save(resolution);
            ReportDto reportDtoToReturn = reportMapper.entityToDto(savedReport);
//            reportDtoToReturn.getResolution().setCreatorId(creatorId);
//            reportDtoToReturn.getResolution().setCuratorId(curatorId);
//            reportDtoToReturn.getResolution().setSignerId(signerId);
            log.info("Отчет сохранен в базе данных");
            return reportDtoToReturn;
        } catch (Exception e) {
            log.error("Ошибка при сохранении отчета в базе данных");
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
