package org.example.mapper;

import org.example.dto.TaskForEmployeeDto;
import org.example.entity.TaskForEmployee;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью TaskForEmployee и объектом TaskForEmployeeDTO.
 */
@Mapper(componentModel = "spring")
public interface TaskForEmployeeMapper extends AbstractMapper<TaskForEmployee, TaskForEmployeeDto> {
}
