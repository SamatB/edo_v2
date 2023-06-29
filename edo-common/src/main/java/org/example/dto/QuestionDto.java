package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

/**
 * DTO класс для сущности Question
 */
@Data
@Schema(description = "Вопрос")
public class QuestionDto {
    @NotNull
    @Schema(description = "Идентификатор вопроса")
    private Long id;

    @NotNull
    @Schema(description = "Дата создания вопроса")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивирования вопроса")
    private ZonedDateTime archivedDate;

    @Schema(description = "Содержание вопроса")
    private String summary;
}
