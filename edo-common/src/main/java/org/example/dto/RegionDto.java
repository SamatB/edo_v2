package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Регион")
public class RegionDto {

    @Schema(description = "идентификатор региона")
    private Long id;

    @NotNull
    @Schema(description = "Наименование региона")
    private String regionName;

    @NotNull
    @Schema(description = "Наименование федерального округа")
    private String federalDistrictName;
}
