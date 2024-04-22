package org.example.mapper;

import org.example.dto.MatchingBlockDto;
import org.example.entity.MatchingBlock;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью MatchingBlock и объектом MatchingBlockDto.
 */

@Mapper(componentModel = "spring", uses = {ParticipantMapper.class})

public interface MatchingBlockMapper extends AbstractMapper<MatchingBlock, MatchingBlockDto> {

    @Override
    @Mapping(target = "agreementList.signatory", ignore = true)
    @Mapping(target = "agreementList.coordinating", ignore = true)

    MatchingBlockDto entityToDto(MatchingBlock matchingBlock);

    @Override
    @Mapping(target = "agreementList.signatory", ignore = true)
    @Mapping(target = "agreementList.coordinating", ignore = true)
    MatchingBlock dtoToEntity(MatchingBlockDto matchingBlockDto);

}
