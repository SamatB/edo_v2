/**
 * Класс описывающий департамент
 */


package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Department extends BaseEntity {

    /**
     * Полное название департамента
     */
    @NotNull
    @Column(name = "full_name")
    private String fullName;

    /**
     * Сокращенное название департамента
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * Адрес департамента
     */
    @Column(name = "address")
    private String address;

    /**
     * Связь с сущностью Address
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @Fetch(FetchMode.JOIN)
    private Address addressDetails;

    /**
     * Номер телефона департамента
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Идентификатор из внешнего хранилища
     */
    @Column(name = "external_id")
    private String externalId;

    /**
     * Вышестоящий департамент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_department_id")
    private Department department;

    /**
     * Дата создания
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата архивации
     */
    @Column(name = "archived_date")
    @UpdateTimestamp
    private ZonedDateTime archivedDate;
}