package org.example.repository.mapper;

import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью Department и объектом DepartmentDto.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, DepartmentMapper.class})
public interface DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {
    //Избавляемся от возможной рекурсии, если вложен более чем один родительский департамент
    @Override
    @Mapping(target = "department.department", ignore = true)
    DepartmentDto entityToDto(Department department);

    @Override
    @Mapping(target = "department.department", ignore = true)
    Department dtoToEntity(DepartmentDto departmentDto);
}
