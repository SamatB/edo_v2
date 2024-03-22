/**
 * Маппер для преобразования между сущностью Appeal и объектом AppealDto.
 */

package org.example.mapper;

import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Маппер преобразует AgreementList в AgreementListDto и обратно
 */
@Mapper(componentModel = "spring", uses = {AppealMapper.class, ParticipantMapper.class, MatchingBlockMapper.class})
public interface AgreementListMapper extends AbstractMapper<AgreementList, AgreementListDto> {
    /**
     * Эти 2 метода нужны как костыль пока MapStruct не починит проблему с использованием других мапперов
     * На момент их добавления не работает @Autowire для AppealMapper
     * Без него поле с типом Appeal не маппится и становится null
     */
    @Override
    @Mapping(source = "appeal.id", target = "appealDto.id")
    AgreementListDto entityToDto(AgreementList agreementList);

    @Override
    @Mapping(source = "appealDto.id", target = "appeal.id")
    AgreementList dtoToEntity(AgreementListDto agreementListDto);
}