package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.dto.DepartmentDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Nomenclature extends BaseEntity{

    /**
     * Код-номер обращения (первая часть номера)
     */
    @Column(name = "nomenclature_index")
    private String index;

    /**
     * Описание
     */
    @Column
    private String description;

    /**
     * Департамент
     */
    @JoinColumn(name = "department_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Department department;

    /**
     * Шаблон номера документа
     */
    @Column
    private String template;

    /**
     * Текущее максимальное значение для номера в рамках номенклатурного индекса
     */
    @Column(name = "current_value")
    private Long currentValue;

    /**
     * Дата переноса в архив
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;
    /**
     * Дата создания
     */
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

}
