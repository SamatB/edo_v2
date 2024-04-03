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

    /**
     * Не трогать - упадет при маппинге листа согласования!
     * Эти 2 метода исправляют проблемы с бесконечной рекурсией при обращении к полям AgreementList и AgreementListDto во время маппинга.
     */
    @Override
    @Mapping(target = "agreementList", ignore = true)
    MatchingBlockDto entityToDto(MatchingBlock matchingBlock);

    @Override
    @Mapping(target = "agreementList", ignore = true)
    MatchingBlock dtoToEntity(MatchingBlockDto matchingBlockDto);
}
