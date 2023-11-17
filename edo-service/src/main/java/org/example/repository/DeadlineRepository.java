/**
 * Репозиторий для работы с сущностью Deadline.
 */
package org.example.repository;

import org.example.entity.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {

    /**
     * Установка даты дедлайна в базе данных по id.
     *
     * @param resolutionId          - id резолюции,
     * @param DateTime    - дата дедлайна
     * @param description - описание причины переноса дедлайна
     */
    @Modifying
    @Transactional
    @Query("UPDATE Deadline dl SET dl.deadlineDate = :DateTime, dl.descriptionOfDeadlineMoving = :description WHERE dl.resolution.id = :id")
    void setDeadlineDate(@Param("id") Long resolutionId, @Param("DateTime") ZonedDateTime DateTime, @Param("description") String description);

    /**
     * Поиск в БД строк по идентификатору резолюции
     */
    Deadline findByResolutionId(Long resolutionId);
}
