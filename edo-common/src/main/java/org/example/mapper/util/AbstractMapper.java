/**
 * Абстрактынй класс маппера для преобразования одного типа данных в другой
 * От данного класса будут наследоваться другие мапперы
 */


package org.example.mapper.util;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

import java.util.List;

@MapperConfig(componentModel = "spring")
public abstract class AbstractMapper<Entity, Dto> {

    /**
     * Метод преобразует сущность JPA в DTO объект
     */

    public abstract Dto EntityToDto(Entity entity);

    /**
     * Метод преобразует DTO объект в сущность JPA
     */
    @InheritConfiguration(name = "EntityToDto")
    public abstract Entity DtoToEntity(Dto dto);

    /**
     * Метод обновляет поля сущности JPA на основе переданного DTO объекта
     */
    public abstract void updateEntity(Dto dto, @MappingTarget Entity entity);

    /**
     * Метод преобразует список сущностей JPA в список сущностей DTO
     */

    public abstract List<Dto> EntityListToDtoList(List<Entity> entities);

    /**
     * Метод преобразует список объектов DTO в список сущностей JPA
     */
    @InheritConfiguration(name = "EntityListToDtoList")
    public abstract List<Entity> DtoListToEntityList(List<Dto> dtos);
}
