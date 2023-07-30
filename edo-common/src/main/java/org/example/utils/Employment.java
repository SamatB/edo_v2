package org.example.utils;

import lombok.AllArgsConstructor;


/**
 * Enum типов трудоустройства
 */
@AllArgsConstructor
public enum Employment {
    UNEMPLOYED("Безработный"),
    WORKER("Работник"),
    STUDENT("Учащийся");
    private final String rusEmployment;

    /**
     * Получение поля с русским названием статуса трудоустройства
     */
    public String getRusEmployment() {
        return rusEmployment;
    }
}
