package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Данный класс описывает задание для сотрудника, он не хранится в БД,
 * создается, затем отправляется в теле ответа клиенту.
 * Методы setter были специально созданы так, чтобы возвращался тип String
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Задание для сотрудника")
public class TaskForEmployeeDto {

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
    private String taskCreationDate;

    @Schema(description = "UUID файла-факсимиле пользователя, создавшего задание")
    private String uuid;

    public String setTaskCreatorFirstName(String taskCreatorFirstName) {
        this.taskCreatorFirstName = taskCreatorFirstName;
        return taskCreatorFirstName;
    }

    public String setTaskCreatorLastName(String taskCreatorLastName) {
        this.taskCreatorLastName = taskCreatorLastName;
        return taskCreatorLastName;
    }

    public String setTaskCreatorMiddleName(String taskCreatorMiddleName) {
        this.taskCreatorMiddleName = taskCreatorMiddleName;
        return taskCreatorMiddleName;
    }

    public String setTaskCreatorEmail(String taskCreatorEmail) {
        this.taskCreatorEmail = taskCreatorEmail;
        return taskCreatorEmail;
    }

    public String setTaskCreatorPhoneNumber(String taskCreatorPhoneNumber) {
        this.taskCreatorPhoneNumber = taskCreatorPhoneNumber;
        return taskCreatorPhoneNumber;
    }
}
