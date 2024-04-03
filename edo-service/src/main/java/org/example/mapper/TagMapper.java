package org.example.mapper;

import org.example.dto.TagDto;
import org.example.entity.Tag;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Tag и объектом TagDto.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends AbstractMapper<Tag, TagDto> {

}
