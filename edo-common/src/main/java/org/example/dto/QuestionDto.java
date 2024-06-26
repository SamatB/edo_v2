package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import org.example.enums.StatusType;

import java.time.ZonedDateTime;

/**
 * DTO класс для сущности Question
 */
@Data
@Schema(description = "Вопрос")
@Builder
public class QuestionDto {

    @Schema(description = "Идентификатор вопроса")
    private Long id;

    @NotNull
    @Schema(description = "Дата создания вопроса")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивирования вопроса")
    private ZonedDateTime archivedDate;

    @Schema(description = "Статус вопроса")
    private StatusType statusType;

    @Schema(description = "Содержание вопроса")
    private String summary;

    @Schema(description = "Идентификатор обращения")
    private Long appealId;
}
