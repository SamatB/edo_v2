package org.example.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Обращение")
public class AppealDto {

    @NotNull
    @Schema(description = "Дата создания обращения")
    private ZonedDateTime creationDate;

    @Schema(description = "Дата архивирования обращения")
    private ZonedDateTime archivedDate;

    @NotNull
    @Schema(description = "Номер обращения")
    private String number;

    @Schema(description = "Описание обращения")
    private String annotation;
}
