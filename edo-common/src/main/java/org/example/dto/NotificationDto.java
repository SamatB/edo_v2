package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.NotificationType;

import java.time.ZonedDateTime;

/**
 * DTO для сущности Notification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для сущности Notification")
public class NotificationDto {

    @Schema(description = "Идентификатор оповещения")
    private Long id;

    @Schema(description = "Дата создания оповещения")
    private ZonedDateTime creationDate;

    @Schema(description = "Тип оповещения")
    private NotificationType type;

    @Schema(description = "Идентификатор сотрудника, которому предназначено оповещение")
    private Long employeeId;
}
