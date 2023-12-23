package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

/**
 * Сущность Question представляет вопросы
 */
@Entity
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Question extends BaseEntity {
    /**
     * Дата создания вопроса
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    ZonedDateTime creationDate;

    /**
     * Дата архивирования вопроса
     */
    @Column(name = "archived_date")
    @UpdateTimestamp
    ZonedDateTime archivedDate;

    /**
     * Содержание вопроса
     */
    @Column(name = "summary", length = 1000)
    String summary;

    /**
     * Связь с сущностью Обращение
     */
    @ManyToOne
    @JoinColumn(name = "appeal_id")
    private Appeal appeal;
}
