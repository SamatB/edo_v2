package org.example.utils;

/**
 * Enum типов трудоустройства
 */
public enum Employment {
    UNEMPLOYED("Безработный"),
    WORKER("Работник"),
    STUDENT("Учащийся");
    private final String rusEmployment;

    Employment(String rusEmployment) {
        this.rusEmployment = rusEmployment;
    }

    /**
     * Получение поля с русским названием статуса трудоустройства
     */
    public String getRusEmployment() {
        return rusEmployment;
    }
}
