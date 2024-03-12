package org.example.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.StatusType;


import java.time.ZonedDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Обращение")
public class AppealDto {

    @Schema(description = "идентификатор обращения")
    private Long id;

    @NotNull
    @Schema(description = "Дата создания обращения")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата регистрации обращения")
    private ZonedDateTime registrationDate;

    @Schema(description = "Дата архивирования обращения")
    private ZonedDateTime archivedDate;

    @NotNull
    @Schema(description = "Номер обращения")
    private String number;

    @Schema(description = "Зарезервированный номер обращения")
    private String reservedNumber;

    @Schema(description = "Описание обращения")
    private String annotation;

    @Schema(description = "Статус обращения")
    private StatusType statusType;

    @Schema(description = "Исполнители")
    private List<EmployeeDto> singers;

    @Schema(description = "Создатель")
    private EmployeeDto creator;

    @Schema(description = "Адресаты")
    private List<EmployeeDto> addressee;
}
