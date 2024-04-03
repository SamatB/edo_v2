package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TagDto;
import org.example.entity.Tag;
import org.example.mapper.TagMapper;
import org.example.repository.TagRepository;
import org.example.service.TagService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    /**
     * Метод для сохранения метки в базу данных.
     * Если метка равна null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение обращения используя TagRepository.
     *
     * @param tagDto объект DTO с новыми данными метки, которые требуется сохранить в базе данных.
     * @return объект DTO метки.
     */
    @Override
    public TagDto saveTag(TagDto tagDto) {
        if (tagDto == null){
            throw new IllegalArgumentException("TagDti не может быть null");
        }
        Tag tag = tagMapper.dtoToEntity(tagDto);
        Tag saveTag = tagRepository.save(tag);
        return tagMapper.entityToDto(saveTag);
    }
}

