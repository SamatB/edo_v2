package org.example.utils;

/**
 * Enum типов трудоустройства автора документа
 */
public enum Employment {
    UNEMPLOYED("Безработный"),
    WORKER("Работник"),
    STUDENT("Учащийся");
    private final String rusEmployment;

    Employment(String rusEmployment) {
        this.rusEmployment = rusEmployment;
    }

    public String getRusEmployment() {
        return rusEmployment;
    }
}
