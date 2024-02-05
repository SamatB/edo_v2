package org.example.mapper;

import org.example.dto.ResolutionReportDto;
import org.example.entity.ResolutionReport;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Report и объектом ReportDto.
 */

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ResolutionMapper.class})
public interface ResolutionReportMapper extends AbstractMapper<ResolutionReport, ResolutionReportDto> {
}
