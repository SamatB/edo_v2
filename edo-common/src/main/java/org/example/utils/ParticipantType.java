package org.example.utils;

import lombok.AllArgsConstructor;

/**
 * Enum типа участника согласования
 */
@AllArgsConstructor
public enum ParticipantType {
    INITIATOR("Инициатор"),
    PARTICIPANT("Участник"),
    SIGNER("Подписант");

    /**
     * Русское название типа участника согласования
     */
    private final String rusParticipantType;
}
