/**
 * Репозиторий для работы с сущностью Report.
 */

package org.example.repository;

import org.example.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Modifying
    @Query(value = "INSERT INTO report (creation_date, comment, result, executor_id, resolution_id)" +
            " VALUES (:creation_date, :comment, :result, :executor_id, :resolution_id)", nativeQuery = true)
    void insertReport(@Param("creation_date") ZonedDateTime creation_date,
                      @Param("comment") String comment,
                      @Param("result") Boolean result,
                      @Param("executor_id") Long executor_id,
                      @Param("resolution_id") Long resolution_id);

}
