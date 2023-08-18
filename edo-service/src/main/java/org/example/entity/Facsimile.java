package org.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Сущность Facsimile представляет воспроизведение личной подписи
 * работника на печати-клише для заверения документов.
 */
@Entity
@Table(name = "facsimile")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Facsimile extends BaseEntity {

    /**
     * Владелец подписи Facsimile
     */
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Департамент владельца подписи Facsimile
     */
    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * Описание файла-картинки Facsimile, хранимого на сервере MinIO
     */
    @OneToOne
    @JoinColumn(name = "file_pool_id")
    private FilePool filePool;

    /**
     * Метка архивировации Facsimile
     */
    @Column(name = "archived")
    private boolean archived;
}
