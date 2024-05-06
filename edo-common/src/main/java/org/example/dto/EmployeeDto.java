package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * DTO для сущности Employee.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для сущности Employee")
@ToString
public class EmployeeDto implements Serializable {

    @Schema(description = "Идентификатор сотрудника")
    private Long id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "Отчество сотрудника")
    private String middleName;

    @Schema(description = "Email сотрудника")
    private String email;

    @Schema(description = "Адрес сотрудника")
    private String address;

    @Schema(description = "Подробная информация об адресе сотрудника")
    private AddressDto addressDetails;

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

    @Schema(description = "Департамент сотрудника")
    private DepartmentDto department;
}