package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO для сущности Participant
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для сущности Participant")
public class ParticipantDto implements Serializable {

    @Schema(description = "Тип участника согласования")
    private String type;

    @Schema(description = "Дата создания участника")
    private ZonedDateTime createDate;

    @Schema(description = "Дата, до которой должно быть исполнено")
    private ZonedDateTime untilDate;

    @Schema(description = "Дата получения обращения")
    private ZonedDateTime acceptDate;

    @Schema(description = "Дата завершения действия")
    private ZonedDateTime expiredDate;

    @Schema(description = "Номер по порядку согласования")
    private Long number;

    @Schema(description = "Сотрудник")
    private EmployeeDto employeeDto;
}
