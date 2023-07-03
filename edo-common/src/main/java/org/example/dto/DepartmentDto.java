package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Schema(description = "Департамент")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @NotNull
    @Schema(description = "Идентификатор департамента")
    private Long id;

    @NotNull
    @Schema(description = "Полное название департамента")
    private String fullName;

    @Schema(description = "Сокращенное название департамента")
    private String shortName;

    @Schema(description = "Адрес департамента")
    private String address;

    @Schema(description = "Номер телефона департамента")
    private String phone;

    @NotNull
    @Schema(description = "Идентификатор из внешнего хранилища")
    private String externalId;

    @Schema(description = "Вышестоящий департамент")
    private DepartmentDto department;

    @NotNull
    @Schema(description = "Дата создания")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивации")
    private ZonedDateTime archivedDate;
}