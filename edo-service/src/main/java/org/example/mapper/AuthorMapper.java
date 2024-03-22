package org.example.mapper;

import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Author и объектом AuthorDto.
 */
@Mapper(componentModel = "spring", uses = AppealMapper.class)
public interface AuthorMapper extends AbstractMapper<Author, AuthorDto> {
}
