package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.utils.ResolutionType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Сущность Resolution представляет резолюцию.
 */
@Entity
@Table(name = "resolution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Resolution extends BaseEntity {

    /**
     * Дата создания резолюции.
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата архивации резолюции.
     */
    @Column(name = "archived_date")
    @UpdateTimestamp
    private ZonedDateTime archivedDate;

    /**
     * Дата последнего действия по резолюции.
     */
    @Column(name = "last_action_date")
    private ZonedDateTime lastActionDate;

    /**
     * Тип резолюции.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ResolutionType type;

    /**
     * Создатель резолюции (связь с сущностью Employee).
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;

    /**
     * Подписывающий резолюцию (связь с сущностью Employee).
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signer_id")
    private Employee signer;

    /**
     * Исполнитель(и) резолюции (связь с сущностью Employee).
     */
    @NotNull
    @OneToMany
    @JoinTable(
            name = "resolution_executor",
            joinColumns = @JoinColumn(name = "resolution_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> executors;

    /**
     * Отчет(ы) по резолюции (связь с сущностью Report).
     */
    @OneToMany
    @JoinTable(
            name = "resolution_report",
            joinColumns = @JoinColumn(name = "resolution_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    private Set<Report> reports;

    /**
     * Куратор резолюции (связь с сущностью Employee).
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_id")
    private Employee curator;

    /**
     * Серийный номер резолюции.
     */
    @NotNull
    @Column(name = "serial_number")
    private Integer serialNumber;

    /**
     * Связь с сущностью Вопрос
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
