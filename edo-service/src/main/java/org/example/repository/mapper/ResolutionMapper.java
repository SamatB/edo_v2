package org.example.repository.mapper;

import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Resolution и объектом ResolutionDto.
 */
@Mapper(componentModel = "spring")
public interface ResolutionMapper extends AbstractMapper<Resolution, ResolutionDto> {
}
