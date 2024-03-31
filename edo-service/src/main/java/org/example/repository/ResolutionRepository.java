package org.example.repository;

import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью Resolution.
 */
@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    /**
     * Метод ищет резолюции связанные с обращением через связанную сущность Question
     *
     * @param appealIdentity ID обращения
     * @return Список резолюций
     */
    @Query("SELECT resolution FROM Resolution resolution " +
            "JOIN resolution.question question" +
            " JOIN question.appeal appeal WHERE appeal.id = :appealIdentity")
    List<Resolution> findByAppealIdentity(Long appealIdentity);

    @Modifying
    @Query(value = "UPDATE Resolution r SET r.archivedDate = :dateTime WHERE r.id = :resolutionId")
    void archiveResolution(Long resolutionId, ZonedDateTime dateTime);

    @Query("SELECT e FROM Resolution e WHERE (:archived IS NULL " +
            "OR (:archived = true AND e.archivedDate IS NOT NULL) " +
            "OR (:archived = false AND e.archivedDate IS NULL))")
    List<ResolutionDto> findResolutions(Boolean archived);


}
