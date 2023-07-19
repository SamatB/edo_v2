package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum типов оповещения.
 */
@AllArgsConstructor
@Getter
public enum NotificationType {
    EMAIL("E-mail"),
    PHONE("Телефон"),
    IN_APP("В приложении");

    /**
     * Русское название типа оповещения.
     */
    private final String rusNotificationType;
}
