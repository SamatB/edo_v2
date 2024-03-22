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
@Mapper(componentModel = "spring", uses = {AppealMapper.class})
public interface QuestionMapper extends AbstractMapper<Question, QuestionDto> {

    @Override
    @Mapping(source = "appeal.id", target = "appeal_id")
    QuestionDto entityToDto(Question question);

    @Override
    @Mapping(source = "appeal_id", target = "appeal.id")
    Question dtoToEntity(QuestionDto questionDto);
}
