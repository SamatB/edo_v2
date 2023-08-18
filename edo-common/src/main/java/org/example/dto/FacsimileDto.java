package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для сущности Facsimile.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Факсимиле")
public class FacsimileDto {

    @Schema(description = "Работник, которому принадлежит факсимиле")
    private EmployeeDto employee;

    @Schema(description = "Департамент, указанный в факсимиле")
    private DepartmentDto department;

    @Schema(description = "Описание файла-картинки факсимиле")
    private FilePoolDto filePool;

    @Schema(description = "Метка, указывающая архивирован ли факсимиле")
    private boolean archived;
}
