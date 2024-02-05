package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.utils.ResolutionType;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * DTO для сущности Resolution.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для сущности Resolution")
public class ResolutionDto {

    @Schema(description = "Идентификатор резолюции")
    private Long id;

    @Schema(description = "Дата создания резолюции")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивации резолюции")
    private ZonedDateTime archivedDate;

    @Schema(description = "Дата последнего действия по резолюции")
    private ZonedDateTime lastActionDate;

    @Schema(description = "Тип резолюции")
    private ResolutionType type;

    @Schema(description = "Идентификатор создателя резолюции")
    private Long creatorId;

    @Schema(description = "Идентификатор подписывающего резолюцию")
    private Long signerId;

    @Schema(description = "Список идентификаторов исполнителей резолюции")
    private Set<Long> executorIds;

    @Schema(description = "Идентификаторы отчета(ов) по резолюции")
    private Set<Long> reportIds;

    @Schema(description = "Идентификатор куратора резолюции")
    private Long curatorId;

    @Schema(description = "Серийный номер резолюции")
    private Integer serialNumber;

    @Schema(description = "Идентификатор вопроса")
    private Long questionId;
}
