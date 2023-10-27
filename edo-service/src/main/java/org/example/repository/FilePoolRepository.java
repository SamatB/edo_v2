package org.example.repository;

import org.example.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью FilePool.
 */
public interface FilePoolRepository extends JpaRepository<FilePool, Long> {

    @Query("SELECT fp FROM FilePool fp JOIN fp.appeal a WHERE a.creationDate <= :date")
    List<FilePool> findFilePoolByCreationDateBefore(@Param("date") ZonedDateTime date);
}
