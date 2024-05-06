package org.example.mapper;

import org.example.dto.DeadlineDto;
import org.example.entity.Deadline;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = ResolutionMapper.class)
public interface DeadlineMapper extends AbstractMapper<Deadline, DeadlineDto> {
}
