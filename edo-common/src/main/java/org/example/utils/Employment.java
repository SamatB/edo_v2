package org.example.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/**
 * Enum типов трудоустройства
 */
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
