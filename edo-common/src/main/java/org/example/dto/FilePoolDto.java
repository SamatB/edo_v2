package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.FilePoolType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Хранимые файлы")
public class FilePoolDto {

    @Schema(description = "id файла в таблице")
    private Long id;

    @Schema(description = "тип файла")
    private FilePoolType fileType;

    @Schema(description ="uuid файла")
    private UUID storageFileId;

    @NotNull
    @Schema(description ="название фала")
    private String name;

    @NotNull
    @Schema(description ="расширение файла")
    private String extension;

    @NotNull
    @Schema(description ="размер файла")
    private Long size;

    @NotNull
    @Schema(description ="количество страниц файла")
    private Integer pageCount;

    @NotNull
    @Schema(description ="дата и время загрузки файла")
    private ZonedDateTime uploadDate;

    @Schema(description ="дата архивации файла")
    private ZonedDateTime archivedDate;

    @Schema(description = "Обращение к которому прикреплен файл")
    private AppealDto appeal;
}
