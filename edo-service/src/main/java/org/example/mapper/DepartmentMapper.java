package org.example.mapper;

import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Department и объектом DepartmentDto.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {
}
