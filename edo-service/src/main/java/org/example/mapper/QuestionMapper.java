package org.example.mapper;

import org.example.dto.NotificationDto;
import org.example.dto.QuestionDto;
import org.example.entity.Notification;
import org.example.entity.Question;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер преобразует Question в QuestionDto и обратно
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper extends AbstractMapper<Question, QuestionDto> {

    @Override
    @Mapping(source = "appeal.id", target = "appealId")
    @Mapping(target = "appeal.questions", ignore = true)
    QuestionDto entityToDto(Question question);

    @Override
    @Mapping(source = "appealId", target = "appeal.id")
    @Mapping(target = "appeal.questions", ignore = true)
    Question dtoToEntity(QuestionDto questionDto);
}
