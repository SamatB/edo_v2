package org.example.repository;

import org.example.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью FilePool.
 */
public interface FilePoolRepository extends JpaRepository<FilePool, Long> {
    /**
     * Выбор filePool-ов по дате связанной сущности.
     *
     * @param date - дата создания обращения.
     */
    @Query("SELECT fp FROM FilePool fp JOIN fp.appeal a WHERE a.creationDate <= :date and fp.removed = false")
    List<FilePool> findFilePoolByCreationDateBefore(@Param("date") ZonedDateTime date);

    /**
     * Изменение статуса наличия файла в MinIO.
     *
     * @param uuidList - список UUID из MinIO, которые были удалены.
     */
    @Modifying
    @Query("UPDATE FilePool fp SET fp.removed = true WHERE fp.storageFileId IN :uuidList")
    void changeRemovedField(@Param("uuidList") List<UUID> uuidList);
}
