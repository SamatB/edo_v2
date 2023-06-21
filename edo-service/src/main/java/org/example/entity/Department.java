/**
 * Класс описывающий департамент
 */


package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {

    /**
     * Идентификатор департамента
     */
    @Id
    @NotNull
    @Column(name = "external_id")
    private String externalId;

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
     * Номер телефона департамента
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Вышестоящий департамент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    /**
     * Подчиненные департаменты
     */
    @OneToMany(mappedBy = "department")
    private Set<Department> subordinateDepartments = new HashSet<>();

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
