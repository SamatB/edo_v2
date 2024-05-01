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

    @Query("SELECT f FROM Facsimile f JOIN Employee e ON f.employee.id=e.id WHERE e.id=:id")
    Facsimile findByEmployeeId(Long id);

    @Query("SELECT f FROM Facsimile f JOIN FilePool fp ON f.filePool.storageFileId=fp.storageFileId WHERE fp.storageFileId=:id")
    Facsimile getReferenceById(String id);
}
