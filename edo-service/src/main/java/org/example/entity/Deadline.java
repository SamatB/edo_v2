package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

/**
 * Сущность Deadline представляет дэдлайн.
 */
@Entity
@Table(name = "deadline")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Deadline extends BaseEntity {

    /**
     * Дэдлайн для резолюции (дата дэдлайна)
     */
    @Column(name = "deadline_date")
    private ZonedDateTime deadlineDate;

    /**
     * Описание причины переноса дедлайна
     */
    @Column(name = "description_of_deadline_moving")
    private String descriptionOfDeadlineMoving;

    /**
     * связь один к одному к таблице Resolution
     * резолюция
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resolution")
    private Resolution resolution;
}
