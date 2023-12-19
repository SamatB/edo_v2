package org.example.mapper;

import org.example.dto.QuestionDto;
import org.example.entity.Question;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер преобразует Question в QuestionDto и обратно
 */
@Mapper(componentModel = "spring", uses = {AppealMapper.class})
public interface QuestionMapper extends AbstractMapper<Question, QuestionDto> {
}
