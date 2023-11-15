package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * DTO-класс сущности Deadline
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дедлайн")
public class DeadlineDto {

    @Schema(description = "идентификатор дедлайна")
    private Long id;

    @Schema(description = "дата дедлайна")
    private ZonedDateTime deadlineDate;

    @Schema(description = "причины переноса дедлайна")
    private String descriptionOfDeadlineMuving;

    @Schema(description = "резолюция для которой выставляется дедлайн")
    private ResolutionDto resolution;

    @Schema(description = "резолюция для которой выставляется дедлайн")
    private List<EmployeeDto> singers;
}

