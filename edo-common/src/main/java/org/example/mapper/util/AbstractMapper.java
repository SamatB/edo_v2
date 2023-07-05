/**
 * Интерфейс маппера для преобразования одного типа данных в другой
 * Данный интерфейс будут реализовывать другие мапперы
 */


package org.example.mapper.util;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

import java.util.List;

@MapperConfig(componentModel = "spring")
public interface AbstractMapper<Entity, Dto> {

    /**
     * Метод преобразует сущность JPA в DTO объект
     */

    Dto EntityToDto(Entity entity);

    /**
     * Метод преобразует DTO объект в сущность JPA
     */
    @InheritConfiguration(name = "EntityToDto")
    Entity DtoToEntity(Dto dto);

    /**
     * Метод обновляет поля сущности JPA на основе переданного DTO объекта
     */
    void updateEntity(Dto dto, @MappingTarget Entity entity);

    /**
     * Метод преобразует список сущностей JPA в список сущностей DTO
     */

    List<Dto> EntityListToDtoList(List<Entity> entities);

    /**
     * Метод преобразует список объектов DTO в список сущностей JPA
     */
    @InheritConfiguration(name = "EntityListToDtoList")
    List<Entity> DtoListToEntityList(List<Dto> dtos);
}
