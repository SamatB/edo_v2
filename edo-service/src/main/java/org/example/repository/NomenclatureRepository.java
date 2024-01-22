package org.example.repository;

import org.example.entity.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Nomenclature n SET n.archivedDate = :currentDateTime WHERE n.id = :id")
    void setArchiveDate(@Param("id") Long id, @Param("currentDateTime") ZonedDateTime currentDateTime);

    @Modifying
    @Transactional
    @Query("UPDATE Nomenclature n SET n.archivedDate = null WHERE n.id = :id")
    void setNullLikeNotArchived(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Nomenclature n SET n.currentValue = n.currentValue + 1 WHERE n.id = :id")
    void incrementCurrentValue();

}

