package org.example.dto;

import lombok.Data;

import java.util.List;


/**
 * Класс DTO для представления данных электронной почты.
 * Используется для передачи данных необходимых для рассылки электронных писем
 * через систему или внешние сервисы.
 ***/
@Data
public class EmailDto {

    private List<String> email;
    private String subject;
    private String text;

}
