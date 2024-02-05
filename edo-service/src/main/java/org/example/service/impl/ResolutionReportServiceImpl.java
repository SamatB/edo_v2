package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionReportDto;
import org.example.dto.ResolutionDto;
import org.example.entity.ResolutionReport;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionReportMapper;
import org.example.repository.ResolutionReportRepository;
import org.example.repository.ResolutionRepository;
import org.example.service.ResolutionReportService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResolutionReportServiceImpl implements ResolutionReportService {

    private final ResolutionReportRepository resolutionReportRepository;
    private final ResolutionRepository resolutionRepository;
    private final ResolutionReportMapper resolutionReportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null или резолюция в отчете равна null, то выбрасывается IllegalArgumentException.
     * Метод выполняет сохранение используя ReportRepository.
     *
     * @param resolutionReportDto объект DTO отчета c id = 0.
     * @return объект DTO отчета с присвоенным id (значения ленивых полей - null)
     */
    @Transactional
    @Override
    public ResolutionReportDto saveResolutionReport(ResolutionReportDto resolutionReportDto) {
        log.info("Сохранение отчета в базе данных");
        ResolutionDto resolutionDto;
        try {
            resolutionDto = resolutionReportDto.getResolution();
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
        ResolutionReport resolutionReport = resolutionReportMapper.dtoToEntity(resolutionReportDto);
        ResolutionReport savedResolutionReport = resolutionReportRepository.save(resolutionReport);
        resolution.getResolutionReports().add(savedResolutionReport);
        resolutionRepository.save(resolution);
        ResolutionReportDto resolutionReportDtoToReturn = resolutionReportMapper.entityToDto(savedResolutionReport);
        log.info("Отчет сохранен в базе данных");
        return resolutionReportDtoToReturn;
    }
}
