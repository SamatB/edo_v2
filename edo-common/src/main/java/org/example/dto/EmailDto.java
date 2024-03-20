package org.example.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import java.util.List;


/**
 * Класс DTO для представления данных электронной почты.
 * Используется для передачи данных необходимых для рассылки электронных писем
 * через систему или внешние сервисы.
 ***/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Email")
public class EmailDto implements Serializable {

    @Schema(description="Список адресов для рассылки")
    private List<String> email;

    @Schema(description = "Идентификатор получателя")
    private Long employeeId;

    @Schema(description = "Адрес электронной почты отправителя")
    private String from;

    @Schema(description = "Адрес электронной почты получателя")
    private String to;

    @Schema(description = "Тема письма")
    private String subject;

    @Schema(description = "Тело письма")
    private String body;
}
