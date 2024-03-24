/**
 * Маппер для преобразования между сущностью AgreementList и объектом AgreementListDto.
 */

package org.example.mapper;

import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;


/**
 * Маппер преобразует AgreementList в AgreementListDto и обратно
 */
@Mapper(componentModel = "spring", uses = {AppealMapper.class, ParticipantMapper.class, MatchingBlockMapper.class})
public interface AgreementListMapper extends AbstractMapper<AgreementList, AgreementListDto> {
}