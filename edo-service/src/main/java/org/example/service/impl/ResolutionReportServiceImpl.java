package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.dto.ResolutionReportDto;
import org.example.entity.ResolutionReport;
import org.example.mapper.ResolutionReportMapper;
import org.example.repository.ResolutionReportRepository;
import org.example.service.ResolutionReportService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResolutionReportServiceImpl implements ResolutionReportService {

    private final ResolutionReportRepository resolutionReportRepository;
    private final ResolutionReportMapper resolutionReportMapper;

    /**
     * Метод для сохранения отчета по резолюции в базе данных.
     * Если отчет равен null или резолюция в отчете равна null, то выбрасывается IllegalArgumentException.
     *
     * @param resolutionReportDto объект DTO отчета c id = 0.
     * @return объект DTO отчета с присвоенным id (значения ленивых полей - null)
     */
    @Transactional
    @Override
    public ResolutionReportDto saveResolutionReport(ResolutionReportDto resolutionReportDto) {
        log.info("Сохранение отчета в базе данных");
        ResolutionDto resolutionDto =
                Optional.ofNullable(resolutionReportDto)
                        .orElseThrow(() -> new IllegalArgumentException("Отчет не сохранен в БД - отчет не должен быть null"))
                        .getResolution();
        Optional.ofNullable(resolutionDto)
                .orElseThrow(() -> new IllegalArgumentException("Отчет не сохранен в БД - ссылка на резолюцию не должна быть null"));
        ResolutionReport resolutionReport = resolutionReportMapper.dtoToEntity(resolutionReportDto);
        ResolutionReport savedResolutionReport = resolutionReportRepository.save(resolutionReport);
        log.info("Отчет сохранен в базе данных");
        return resolutionReportMapper.entityToDto(savedResolutionReport);
    }
}
