package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Schema(description = "Департамент")
public class DepartmentDto implements Serializable {

    @Schema(description = "Идентификатор департамента")
    private Long id;
    @NotNull
    @Schema(description = "Полное название департамента")
    private String fullName;
    @Schema(description = "Сокращенное название департамента")
    private String shortName;
    @Schema(description = "Адрес департамента")
    private String address;
    @Schema(description = "Подробная информации об адресе департамента")
    private AddressDto addressDetails;
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