package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "Номенклатура")
public class NomenclatureDto implements Serializable {

        @Schema(name = "index")
        private String index;

        @Schema(name = "description")
        private String description;

        @Schema(name = "department")
        private DepartmentDto department;

        @Schema(name = "template")
        private String template;

        @Schema(name = "currentValue")
        private Long currentValue;

        @Schema(name = "archivedDate")
        private ZonedDateTime archivedDate;

        @Schema(name = "creationDate")
        private ZonedDateTime creationDate;
}
