package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum статусов обращения
 */
@AllArgsConstructor
@Getter
public enum AppealStatus {
    NEW("новый"),
    REGISTERED("зарегистрирован"),
    EDITED("отредактирован"),
    ARCHIVED("в архиве");

    /**
     * Русское название статуса обращения
     */
    private final String rusAppealStatus;
}
