package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Класс хранимых файлов
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
    @Column(name = "name", length = 255)
    private String name;
    /**
     * расширение файла
     * */
    @NotNull
    @Column(name = "extension", length = 4)
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
