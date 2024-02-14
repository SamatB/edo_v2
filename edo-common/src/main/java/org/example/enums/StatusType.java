package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum статусов обращений и вопросов
 */
@AllArgsConstructor
@Getter
public enum StatusType {
    REGISTERED("Зарегистрирован"),
    NOT_REGISTERED("Не зарегистрирован"),
    ON_THE_CARPET("На рассмотрении"),
    EXECUTED("Исполнен"),
    PARTIALLY_EXECUTED("Частично исполнен"),
    SENT_FOR_RECOGNITION("Отправлен на распознавание"),
    ARCHIVE("Архив"),
    REMOVED("Удален");

    /**
     * Русское название типа статуса
     */
    private final String rusStatusType;
}
