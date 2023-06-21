package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Департамент")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @NotNull
    @Schema(description = "Идентификатор департамента")
    private String externalId;

    @NotNull
    @Schema(description = "Полное название департамента")
    private String fullName;

    @Schema(description = "Сокращенное название департамента")
    private String shortName;

    @Schema(description = "Адрес департамента")
    private String address;

    @Schema(description = "Номер телефона департамента")
    private String phone;

    @Schema(description = "Вышестоящий департамента")
    private DepartmentDto department;

    @Schema(description = "Подчиненный департаменты")
    private Set<DepartmentDto> subordinateDepartments = new HashSet<>();

    @NotNull
    @Schema(description = "Дата создания")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивации")
    private ZonedDateTime archivedDate;
}
