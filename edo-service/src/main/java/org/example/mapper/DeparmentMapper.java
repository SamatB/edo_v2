package org.example.mapper;

import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeparmentMapper extends AbstractMapper<Department, DepartmentDto> {
}
