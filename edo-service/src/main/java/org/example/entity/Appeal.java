package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "appeal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appeal extends BaseEntity {


    /**
     * Дата создания обращения
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата архивирования обращения
     */
    @Column(name = "archived_date")
    @UpdateTimestamp
    private ZonedDateTime archivedDate;

    /**
     * Номер обращения
     */
    @NotNull
    @Column(name = "number")
    private String number;

    /**
     * Описание обращения
     */
    @Column(name = "annotation")
    private String annotation;

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
}
