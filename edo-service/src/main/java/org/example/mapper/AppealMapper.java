package org.example.mapper;

import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Appeal и объектом AppealDto.
 */
@Mapper(componentModel = "spring")
public interface AppealMapper extends AbstractMapper<Appeal, AppealDto> {
}
