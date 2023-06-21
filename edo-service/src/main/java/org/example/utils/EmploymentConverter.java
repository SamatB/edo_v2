package org.example.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

/**
 * Конвертер для JPA: Enum типов трудоустройства автора документа - атрибут - русское наименование типа
 */
@Converter(autoApply = true)
public class EmploymentConverter implements AttributeConverter<Employment, String> {

    @Override
    public String convertToDatabaseColumn(Employment employment) {
        if (employment == null) {
            return null;
        }
        return employment.getRusEmployment();
    }

    @Override
    public Employment convertToEntityAttribute(String rusEmployment) {
        if (rusEmployment == null) {
            return null;
        }

        return Stream.of(Employment.values())
                .filter(c -> c.getRusEmployment().equals(rusEmployment))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

