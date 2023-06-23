package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Сущность Employee представляет сотрудника.
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Employee extends BaseEntity {

    /**
     * Имя сотрудника.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия сотрудника.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество сотрудника.
     */
    @Size(max = 100)
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Адрес сотрудника.
     */
    @Size(max = 200)
    @Column(name = "address")
    private String address;

    /**
     * URL фотографии сотрудника.
     */
    @Size(max = 200)
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * ФИО сотрудника в дательном падеже.
     */
    @Size(max = 200)
    @Column(name = "fio_dative")
    private String fioDative;

    /**
     * ФИО сотрудника в именительном падеже.
     */
    @Size(max = 200)
    @Column(name = "fio_nominative")
    private String fioNominative;

    /**
     * ФИО сотрудника в родительном падеже.
     */
    @Size(max = 200)
    @Column(name = "fio_genitive")
    private String fioGenitive;

    /**
     * Внешний идентификатор сотрудника.
     */
    @Size(max = 100)
    @Column(name = "external_id")
    private String externalId;

    /**
     * Номер мобильного телефона сотрудника.
     */
    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    /**
     * Рабочий номер телефона сотрудника.
     */
    @Size(max = 20)
    @Column(name = "work_phone")
    private String workPhone;

    /**
     * Дата рождения сотрудника.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Имя пользователя сотрудника.
     */
    @Size(max = 100)
    @Column(name = "username")
    private String username;

    /**
     * Дата создания сотрудника.
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата архивации сотрудника.
     */
    @Column(name = "archived_date")
    @UpdateTimestamp
    private ZonedDateTime archivedDate;
}