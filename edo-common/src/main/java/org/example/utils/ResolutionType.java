package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum типов резолюции.
 */
@AllArgsConstructor
@Getter
public enum ResolutionType {
    RESOLUTION("Резолюция"),
    DIRECTION("Направление"),
    REQUEST("Запрос");

    /**
     * Русское название типа резолюции.
     */
    private final String rusResolutionType;
}