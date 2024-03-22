package org.example.mapper;

import org.example.dto.RegionDto;
import org.example.entity.Region;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Region и объектом RegionDto.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends AbstractMapper<Region, RegionDto> {
}
