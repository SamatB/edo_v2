package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.enums.StatusType;
import org.hibernate.annotations.CreationTimestamp;

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
    @NotNull
    @Column(name = "number")
    private String number;


    /**
     * Зарезервированный номер обращения
     */
//    @Pattern(regexp = "^[\\w&&[^\\d]&&[^_]]+-[0-9]{4}//[0-9]+-[0-9]+$", message = "Неверный формат номера обращения")
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
     * свзязь один ко многим к таблице Employee
     * исполнитель
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appeal_employee_singers"
            , joinColumns = @JoinColumn(name = "id_appeal")
            , inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    private List<Employee> singers;
    /**
     * свзязь один к одному к таблице Employee
     * создатель
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;
    /**
     * свзязь один ко многим к таблице Employee
     * адресат
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appeal_employee_addressee"
            , joinColumns = @JoinColumn(name = "id_appeal")
            , inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    private List<Employee> addressee;
    /**
     * связь с сущностью Номенклатура
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomenclature_id", referencedColumnName = "id")
    private Nomenclature nomenclature;

    /**
     * связь с сущностью Регион (у одного обращения Appeal может быть один регион Region)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;
}
