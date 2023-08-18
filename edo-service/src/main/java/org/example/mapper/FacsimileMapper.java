package org.example.mapper;

import org.example.dto.FacsimileDto;
import org.example.entity.Facsimile;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Facsimile и объектом FacsimileDto.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class, FilePoolMapper.class})
public interface FacsimileMapper extends AbstractMapper<Facsimile, FacsimileDto> {
}
