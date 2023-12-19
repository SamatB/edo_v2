package org.example.mapper;

import org.example.dto.ResolutionDto;
import org.example.entity.Question;
import org.example.entity.Resolution;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью Resolution и объектом ResolutionDto.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ResolutionMapper extends AbstractMapper<Resolution, ResolutionDto> {
    @Override
    @Mapping(source = "question.id", target = "questionId")
    ResolutionDto entityToDto(Resolution resolution);

    @Override
    @Mapping(source = "questionId", target = "question.id")
    Resolution dtoToEntity(ResolutionDto resolutionDto);
}