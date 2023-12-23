/**
 * Маппер для преобразования между сущностью Appeal и объектом AppealDto.
 */

package org.example.mapper;

import org.example.dto.AgreementListDto;
import org.example.entity.AgreementList;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;


/**
 * Маппер преобразует AgreementList в AgreementListDto и обратно
 */
@Mapper(componentModel = "spring", uses = {AppealMapper.class})
public interface AgreementListMapper extends AbstractMapper<AgreementList, AgreementListDto> {
}