/**
 * Абстрактынй класс маппера для преобразования одного типа данных в другой
 * От данного класса будут наследоваться другие мапперы
 */


package org.example.mapper.util;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class AbstractMapper<S, T> {

    /**
     * Метод преобразует сущность JPA в DTO объект
     */
    public abstract T toDto(S entity);

    /**
     * Метод преобразует DTO объект в сущность JPA
     */
    public abstract S toEntity(T dto);

    /**
     * Метод обновляет поля сущности JPA на основе переданного DTO объекта
     */
    public abstract void updateEntity(T dto, S entity);

    /**
     * Метод преобразует список сущностей JPA в список сущностей DTO
     */
    public abstract List<T> toDtoList(List<S> entities);

    /**
     * Метод преобразует список объектов DTO в список сущностей JPA
     */
    public abstract List<S> toEntityList(List<T> dtos);
}
