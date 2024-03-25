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
import java.util.Collection;

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

    /**
     * Выгрузка дедлайнов резолюций по идентификатору обращения, с учетом нахождения резолюций в архиве
     * @param appealId - идентификатор обращения
     * @param archived - флаг, указывающий на архивацию "0 - все резолюции, 1 - архивные, 2 - не в архиве"
     * @return Список дедлайнов резолюций
     */
    @Query("SELECT dl FROM Deadline dl WHERE (dl.resolution.question.appeal.id = :appealId AND (:archived IS NULL " +
            "OR (:archived = true AND dl.resolution.archivedDate IS NOT NULL)" +
            "OR (:archived = false AND dl.resolution.archivedDate IS NULL)))")
    Collection<Deadline> getDeadlinesByAppeal(Long appealId, Boolean archived);


}
