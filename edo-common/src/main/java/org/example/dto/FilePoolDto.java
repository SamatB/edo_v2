package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Schema(description = "Хранимые файлы")
public class FilePoolDto {

    @Schema(name = "id файла в таблице")
    private Long id;

    @Schema(name ="uuid файла")
    private UUID storageFileId;

    @NotNull
    @Schema(name ="название фала")
    private String name;

    @NotNull
    @Schema(name ="расширение файла")
    private String extension;

    @NotNull
    @Schema(name ="размер файла")
    private Long size;

    @NotNull
    @Schema(name ="количество страниц файла")
    private Integer pageCount;

    @NotNull
    @Schema(name ="дата и время загрузки файла")
    private ZonedDateTime uploadDate;

    @Schema(name ="дата архивации файла")
    private ZonedDateTime archivedDate;
}
