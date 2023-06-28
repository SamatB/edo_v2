package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "singers")
    private List<Employee> singers;
    /**
     * свзязь один к одному к таблице Employee
     * создатель
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    private Employee creator;
    /**
     * свзязь один ко многим к таблице Employee
     * адресат
     */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "addressee")
    private List<Employee> addressee;

    /**
     * метод для добавления взаимосвязи для поля исполнитель(singers)
     * принимает работника(employee)
     * ничего не возвращает
     */
    public void addEmployeeSingers(Employee employee) {
        if (singers == null) {
            singers = new ArrayList<>();
        }
        singers.add(employee);
        employee.setSingersAppeal(this);
    }

    /**
     * метод для добавления взаимосвязи для поля адрессат (addressee)
     * принимает работника(employee)
     * ничего не возвращает
     */
    public void addEmployeeAddressee(Employee employee) {
        if (addressee == null) {
            addressee = new ArrayList<>();
        }
        addressee.add(employee);
        employee.setAddresseeAppeal(this);
    }
}
