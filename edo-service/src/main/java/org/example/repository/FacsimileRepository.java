package org.example.repository;

import org.example.entity.Facsimile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacsimileRepository extends JpaRepository<Facsimile, Long> {

    /**
     * Поиск всех Facsimile в базе данных по статусу архивации.
     * @param isArchived - статус архивации.
     */
    List<Facsimile> findByArchived(boolean isArchived);

    /**
     * Изменение статуса архивации Facsimile по id.
     * @param id - идентификатор Facsimile.
     */
    @Modifying
    @Query("UPDATE Facsimile f SET f.archived = NOT f.archived WHERE f.id = :id")
    void toggleArchivedStatus(@Param("id") Long id);

}
