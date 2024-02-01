package org.example.mapper;

import org.example.dto.ReportDto;
import org.example.entity.Report;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Report и объектом ReportDto.
 */


@Mapper(componentModel = "spring")
public interface ReportMapper extends AbstractMapper<Report, ReportDto> {
}
