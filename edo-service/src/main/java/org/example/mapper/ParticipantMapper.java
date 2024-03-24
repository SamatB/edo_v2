package org.example.mapper;

import org.example.dto.ParticipantDto;
import org.example.entity.Participant;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер преобразует Participant в ParticipantDto и обратно
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ParticipantMapper extends AbstractMapper<Participant, ParticipantDto> {

    @Override
    @Mapping(target = "matchingBlock.participants", ignore = true)
    @Mapping(target = "matchingBlock.agreementList", ignore = true)
    ParticipantDto entityToDto(Participant participant);

    @Override
    @Mapping(target = "matchingBlock.participants", ignore = true)
    @Mapping(target = "matchingBlock.agreementList", ignore = true)
    Participant dtoToEntity(ParticipantDto participantDto);


}
