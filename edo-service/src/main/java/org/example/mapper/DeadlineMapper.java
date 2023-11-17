package org.example.mapper;

import org.example.dto.DeadlineDto;
import org.example.entity.Deadline;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeadlineMapper extends AbstractMapper<Deadline, DeadlineDto> {
}
