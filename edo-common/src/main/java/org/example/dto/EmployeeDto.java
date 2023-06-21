package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * DTO для сущности Employee.
 */
@Data
@Schema(description = "DTO для сущности Employee")
public class EmployeeDto {

    @NotNull
    @Schema(description = "Идентификатор сотрудника")
    private Long id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "Отчество сотрудника")
    private String middleName;

    @Schema(description = "Адрес сотрудника")
    private String address;

    @Schema(description = "URL фотографии сотрудника")
    private String photoUrl;

    @Schema(description = "ФИО сотрудника в дательном падеже")
    private String fioDative;

    @Schema(description = "ФИО сотрудника в именительном падеже")
    private String fioNominative;

    @Schema(description = "ФИО сотрудника в родительном падеже")
    private String fioGenitive;

    @Schema(description = "Внешний идентификатор сотрудника")
    private String externalId;

    @Schema(description = "Номер мобильного телефона сотрудника")
    private String phone;

    @Schema(description = "Рабочий номер телефона сотрудника")
    private String workPhone;

    @Schema(description = "Дата рождения сотрудника")
    private LocalDate birthDate;

    @Schema(description = "Имя пользователя сотрудника")
    private String username;

    @Schema(description = "Дата создания сотрудника")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивации сотрудника")
    private ZonedDateTime archivedDate;
}