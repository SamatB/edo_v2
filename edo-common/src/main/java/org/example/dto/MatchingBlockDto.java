package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ApprovalBlockParticipantType;
import org.example.utils.MatchingBlockType;

import java.util.Set;


/**
 * DTO для сущности MatchingBlock
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для сущности MatchingBlock")

public class MatchingBlockDto {

    @Schema(description = "идентификатор блока участников согласования")
    private Long id;

    @Schema(description = "Номер по порядку согласования и отображения")
    private Long number;

    @Schema(description = "Тип блока согласования")
    private MatchingBlockType matchingBlockType;

    @Schema(description = "Участники согласования")
    private Set<ParticipantDto> participants;

    @Schema(description = "Лист согласования")
    private AgreementListDto agreementList;

    @Schema(description = "Тип блока в листе согласования")
    private ApprovalBlockParticipantType approvalBlockType;
}
