package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

/**
 * Сущность "Задание для сотрудника",
 * которое представляется в печатной форме.
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TaskForEmployee extends BaseEntity {

    /**
     * Имя пользователя, создавшего задание.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String taskCreatorFirstName;

    /**
     * Фамилия пользователя, создавшего задание.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String taskCreatorLastName;

    /**
     * Отчество пользователя, создавшего задание.
     */
    @Size(max = 100)
    private String taskCreatorMiddleName;

    /**
     * Email пользователя, создавшего задание.
     */
    @Size(max = 100)
    private String taskCreatorEmail;

    /**
     * Номер телефона пользователя, создавшего задание.
     */
    @Size(max = 20)
    private String taskCreatorPhoneNumber;

    /**
     * Содержание задания.
     */
    @Size(max = 1000)
    private String taskDescription;

    /**
     * Имя исполнителя задания.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String executorFirstName;

    /**
     * Фамилия исполнителя задания.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String executorLastName;

    /**
     * Отчество исполнителя задания.
     */
    @Size(max = 100)
    private String executorMiddleName;

    /**
     * Дата создания задания.
     */
    @NotNull
    private String taskCreationDate;

    /**
     * Связь с сущностью Facsimile
     */

    private Long taskCreatorFacsimileID;
}
