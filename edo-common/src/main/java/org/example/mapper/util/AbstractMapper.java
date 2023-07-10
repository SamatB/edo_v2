/**
 * Интерфейс маппера для преобразования одного типа данных в другой
 * Данный интерфейс будут реализовывать другие мапперы
 */


package org.example.mapper.util;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

import java.util.Collection;

@MapperConfig(componentModel = "spring")
public interface AbstractMapper<Entity, Dto> {

    /**
     * Метод преобразует сущность JPA в DTO объект
     */

    Dto entityToDto(Entity entity);

    /**
     * Метод преобразует DTO объект в сущность JPA
     */
    Entity dtoToEntity(Dto dto);

    /**
     * Метод обновляет поля сущности JPA на основе переданного DTO объекта
     */
    void updateEntity(Dto dto, @MappingTarget Entity entity);

    /**
     * Метод преобразует коллекцию сущностей JPA в колеекцию сущностей DTO
     */

    Collection<Dto> entityListToDtoList(Collection<Entity> entities);

    /**
     * Метод преобразует коллекцию объектов DTO в колллекцию сущностей JPA
     */
    Collection<Entity> dtoListToEntityList(Collection<Dto> dtos);
}
