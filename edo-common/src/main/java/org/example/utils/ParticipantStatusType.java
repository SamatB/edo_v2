package org.example.utils;


import lombok.AllArgsConstructor;

/**
 * Enum типа статуса участника согласования
 */
@AllArgsConstructor
public enum ParticipantStatusType {
    ACTIVE("Активный"),
    PASSIVE("Пассивный");
    /**
     * Русское название типа статуса участника согласования
     */
    private final String rusParticipantStatusType;
}
