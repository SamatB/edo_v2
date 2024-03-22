package org.example.mapper;

import org.example.dto.ParticipantDto;
import org.example.entity.Participant;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер преобразует Participant в ParticipantDto и обратно
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, MatchingBlockMapper.class})
public interface ParticipantMapper extends AbstractMapper<Participant, ParticipantDto> {

}
