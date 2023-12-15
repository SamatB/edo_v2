package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum типов блока согласования.
 */


@AllArgsConstructor
@Getter
public enum MatchingBlockType {

    PARALLEL("Параллельный"),
    ALTERNATE("Очередный");

    /**
     * Русское название типа блока согласования.
     */

    private final String rusMathingBlockType;


}
