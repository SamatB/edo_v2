package org.example.mapper;

import org.example.dto.QuestionDto;
import org.example.entity.Question;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер преобразует Question в QuestionDto и обратно
 */
@Mapper(componentModel = "spring", uses = {AppealMapperResolver.class})
public interface QuestionMapper extends AbstractMapper<Question, QuestionDto> {
    @Override
    @Mapping(target = "appealId", source = "appeal.id")
    QuestionDto entityToDto(Question question);

    @Override
    @Mapping(source = "appealId", target = "appeal")
    Question dtoToEntity(QuestionDto questionDto);
}
