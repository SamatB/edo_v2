package org.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.utils.FilePoolType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Класс хранимых файлов
 */
@Entity
@Table(name = "file_pool")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FilePool extends BaseEntity {
    /**
     * uuid файла
     */
    @Column(name = "storage_file_id")
    private UUID storageFileId;
    /**
     * название фала
     */
    @NotNull
    @Column(name = "name", length = 255)
    private String name;
    /**
     * расширение файла
     */
    @NotNull
    @Column(name = "extension", length = 4)
    private String extension;
    /**
     * тип файла
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FilePoolType fileType;

    /**
     * размер файла
     */
    @NotNull
    @Column(name = "size")
    private Long size;
    /**
     * количество страниц файла
     */
    @NotNull
    @Column(name = "page_count")
    private Integer pageCount;
    /**
     * дата и время загрузки файла
     */
    @NotNull
    @Column(name = "upload_date")
    private ZonedDateTime uploadDate;
    /**
     * дата архивации файла
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * обращение с которым связан файл
     */
    @ManyToOne()
    @JoinColumn(name = "appeal_id")
    private Appeal appeal;

    /**
     * Метка что файл был удален из MinIO
     */
    @Column(name = "removed")
    private Boolean removed;
}
