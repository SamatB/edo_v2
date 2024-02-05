package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * DTO для сущности Report.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Отчет по резолюции")
public class ReportDto {

    @Schema(description = "Идентификатор отчета")
    private Long id;

    @Schema(description = "Дата создания отчета")
    private ZonedDateTime creationDate;

    @Schema(description = "Комментарий к отчету")
    private String comment;

    @Schema(description = "Итог выполнения")
    private Boolean result;

    @Schema(description = "Исполнитель")
    private EmployeeDto executor;

    @Schema(description = "Резолюция")
    private ResolutionDto resolution;
}
