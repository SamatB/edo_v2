package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "appeal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appeal extends BaseEntity{


    /**
     * Дата создания обращения
     */
    @NotNull
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

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
     * Описание обращения
     */
    @Column(name = "annotation")
    private String annotation;
}
