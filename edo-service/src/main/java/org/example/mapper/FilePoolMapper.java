package org.example.mapper;

import org.example.dto.FilePoolDto;
import org.example.entity.FilePool;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью FilePool и объектом FilePoolDto.
 */
@Mapper(componentModel = "spring", uses = AppealMapper.class)
public interface FilePoolMapper extends AbstractMapper<FilePool, FilePoolDto> {
}
