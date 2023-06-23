package org.example.entity;

import jakarta.persistence.*;
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
     * */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id")
    private List<Employee> singers;
    /**
     * свзязь один ко многим к таблице Employee
     * создатель
     * */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id")
    private Employee creator;
    /**
     * свзязь один ко многим к таблице Employee
     * адресат
     * */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id")
    private List<Employee> addressee;
}
