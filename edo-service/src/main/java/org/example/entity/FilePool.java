package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Класс хранимых файлов
 * @author Artishevskii Aleksey
 * @version 0.1
 * */
@Entity
@Table(name = "file_pool")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilePool extends BaseEntity {
    /**
     *  id файла в таблице
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_file_id")
    private UUID storageFileId;
    /**
     * название фала
     * */
    @NotNull
    @Column(name = "name")
    private String name;
    /**
     * расширение файла
     * */
    @NotNull
    @Column(name = "extension")
    private String extension;
    /**
     * размер файла
     * */
    @NotNull
    @Column(name = "size")
    private Long size;
    /**
     * количество страниц файла
     * */
    @NotNull
    @Column(name = "page_count")
    private Integer pageCount;
    /**
     * дата и время загрузки файла
     * */
    @NotNull
    @Column(name = "upload_date")
    private ZonedDateTime uploadDate;
    /**
     * дата архивации файла
     * */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;
}
