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

import java.time.ZonedDateTime;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Tag extends BaseEntity{

    /**
     * Название метки.
     */
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "name")
    private String name;

    /**
     * Создатель метки (связь с сущностью Employee).
     * Один создатель - множество меток.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;

    /**
     * Дата создания метки.
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Обращение (связь с сущностью Appeal).
     * Одно обращение - множество меток.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_appeal")
    private Appeal appeal;
}
