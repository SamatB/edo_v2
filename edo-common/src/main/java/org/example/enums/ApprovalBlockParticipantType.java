package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Тип блока в листе согласования по участникам
 */
@RequiredArgsConstructor
@Getter
public enum ApprovalBlockParticipantType {
    PARTICIPANT_BLOCK("Блок участников согласования"),
    SIGNER_BLOCK("Блок подписантов");

    private final String name;
}