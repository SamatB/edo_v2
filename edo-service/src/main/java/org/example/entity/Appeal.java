package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.enums.StatusType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "appeal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Appeal extends BaseEntity {


    /**
     * Дата создания обращения
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата регистрации обращения
     */
    @Column(name = "registration_date")
    private ZonedDateTime registrationDate;

    /**
     * Дата архивирования обращения
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Номер обращения
     */
    @Column(name = "number")
    private String number;


    /**
     * Зарезервированный номер обращения
     */
    @Column(name = "reserved_number", unique = true, length = 50)
    private String reservedNumber;

    /**
     * Описание обращения
     */
    @Column(name = "annotation")
    private String annotation;

    /**
     * Статус обращения
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    /**
     * Связь один ко многим к таблице Employee
     * исполнитель
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appeal_employee_singers"
            , joinColumns = @JoinColumn(name = "id_appeal")
            , inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    @Fetch(FetchMode.JOIN)
    private List<Employee> singers;
    /**
     * Связь один к одному к таблице Employee
     * создатель
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    @Fetch(FetchMode.JOIN)
    private Employee creator;
    /**
     * Связь один ко многим к таблице Employee
     * адресат
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appeal_employee_addressee"
            , joinColumns = @JoinColumn(name = "id_appeal")
            , inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    @Fetch(FetchMode.JOIN)
    private List<Employee> addressee;
    /**
     * Связь с сущностью Номенклатура
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomenclature_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Nomenclature nomenclature;

    /**
     * Связь с сущностью Регион (у одного обращения Appeal может быть один регион Region)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    /**
     * Связь один ко многим к таблице Question
     * вопросы
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appeal_questions"
            , joinColumns = @JoinColumn(name = "appeal_id")
            , inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @Fetch(FetchMode.JOIN)
    private List<Question> questions;
}
