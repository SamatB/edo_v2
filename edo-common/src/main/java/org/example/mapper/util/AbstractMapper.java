/**
 * Абстрактынй класс маппера для преобразования одного типа данных в другой
 * От данного класса будут наследоваться другие мапперы
 */


package org.example.mapper.util;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

import java.util.List;

@MapperConfig(componentModel = "spring")
public abstract class AbstractMapper<S, T> {

    /**
     * Метод преобразует сущность JPA в DTO объект
     */
    @InheritInverseConfiguration
    public abstract T EntityToDto(S entity);

    /**
     * Метод преобразует DTO объект в сущность JPA
     */
    public abstract S DtoToEntity(T dto);

    /**
     * Метод обновляет поля сущности JPA на основе переданного DTO объекта
     */
    public abstract void updateEntity(T dto, @MappingTarget S entity);

    /**
     * Метод преобразует список сущностей JPA в список сущностей DTO
     */
    @InheritInverseConfiguration
    public abstract List<T> EntityListtoDtoList(List<S> entities);

    /**
     * Метод преобразует список объектов DTO в список сущностей JPA
     */
    public abstract List<S> DtoListtoEntityList(List<T> dtos);
}
