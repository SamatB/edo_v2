package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

/**
 * Сущность Report представляет отчет по резолюции.
 */
@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Report extends BaseEntity {

    /**
     * Дата создания отчета.
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Комментарий к отчету
     */
    @Column(name = "comment")
    private String comment;

    /**
     * итог выполнения(выполнено или не выполнено)
     */
    @Column(name = "result")
    private Boolean result;

    /**
     * Исполнитель
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private Employee executor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "resolution_id")
    private Resolution resolution;
}
