package org.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * Класс DTO для представления данных электронной почты.
 * Используется для передачи данных необходимых для рассылки электронных писем
 * через систему или внешние сервисы.
 ***/
@Data
public class EmailDto implements Serializable {

    private List<String> email;
    private String subject;
    private String text;

}
