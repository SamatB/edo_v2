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
@Schema(description = "Tag")
public class TagDto {

    @Schema(description = "идентификатор метки")
    private Long id;

    @NotNull
    @Schema(description = "Название метки")
    private String name;

    @NotNull
    @Schema(description = "Идентификатор создателя метки")
    private Long creatorId  ;

    @NotNull
    @Schema(description = "Дата создания метки")
    private ZonedDateTime creationDate;

    @Schema(description = "Обращение")
    private AppealDto appealDto;
}
