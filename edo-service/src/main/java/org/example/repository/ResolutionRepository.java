package org.example.repository;

import org.example.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью Resolution.
 */
@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {

    @Query(value = "UPDATE Resolution res SET res.archivedDate = :now WHERE res.id = :id")
    void archiveResolution(Long id, OffsetDateTime now);

    @Query("SELECT e FROM Resolution e WHERE (:archived IS NULL " +
            "OR (:archived = true AND e.archivedDate IS NOT NULL) " +
            "OR (:archived = false AND e.archivedDate IS NULL))")
    List<Resolution> findResolutions(Boolean archived);
}
