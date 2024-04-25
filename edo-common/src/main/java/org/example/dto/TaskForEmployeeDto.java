package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Задание для сотрудника")
public class TaskForEmployeeDto {

    @Schema(description = "идентификатор задания")
    private Long id;

    @NotNull
    @Schema(description = "Имя пользователя, создавшего задание")
    private String taskCreatorFirstName;

    @NotNull
    @Schema(description = "Фамилия пользователя, создавшего задание")
    private String taskCreatorLastName;

    @Schema(description = "Отчество пользователя, создавшего задание")
    private String taskCreatorMiddleName;

    @Schema(description = "Email пользователя, создавшего задание")
    private String taskCreatorEmail;

    @Schema(description = "Номер телефона пользователя, создавшего задание")
    private String taskCreatorPhoneNumber;

    @Schema(description = "Содержание задания")
    private String taskDescription;

    @NotNull
    @Schema(description = "Имя исполнителя задания")
    private String executorFirstName;

    @NotNull
    @Schema(description = "Фамилия исполнителя задания")
    private String executorLastName;

    @Schema(description = "Отчество исполнителя задания")
    private String executorMiddleName;

    @NotNull
    @Schema(description = "Дата создания задания")
    private ZonedDateTime taskCreationDate;

    @Schema(description = "Факсимиле пользователя, создавшего задание")
    private Long taskCreatorFacsimileId;
}
