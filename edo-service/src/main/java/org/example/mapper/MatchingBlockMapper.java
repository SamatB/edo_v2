package org.example.mapper;

import org.example.dto.MatchingBlockDto;
import org.example.entity.MatchingBlock;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью MatchingBlock и объектом MatchingBlockDto.
 */

@Mapper(componentModel = "spring")
public interface MatchingBlockMapper extends AbstractMapper<MatchingBlock, MatchingBlockDto> {
}
