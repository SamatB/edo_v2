package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * DTO-класс данных Листа согласования
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Лист согласования")
public class AgreementListDto {
    @NotNull
    @Schema(description = "Дата создания")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата направления на согласование")
    private ZonedDateTime sentApprovalDate;

    @Schema(description = "Дата подписания")
    private ZonedDateTime signDate;

    @Schema(description = "Дата возврата на доработку")
    private ZonedDateTime returnedDate;


    @Schema(description = "Дата обработки возврата")
    private ZonedDateTime refundProcessingDate;


    @Schema(description = "Дата архивности")
    private ZonedDateTime archiveDate;


    @Schema(description = "Комментарий инициатора")
    private String comment;


    @Schema(description = "Обращение с которым связан лист согласования")
    private AppealDto appealDto;


    @Schema(description = "Инициатор запуска листа согласования")
    private ParticipantDto participantDto;


    @Schema(description = "Блоки подписантов")
    private Set<MatchingBlockDto> signatoryDto;


    @Schema(description = "Блоки согласующих")
    private Set<MatchingBlockDto> coordinatingDto;

}


