package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.utils.Employment;



/**
 * DTO-класс личных данных автора документа
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Автор")
public class AuthorDto {

    @Schema(description = "идентификатор автора")
    private Long id;

    @NotNull
    @Schema(description = "Имя автора")
    private String firstName;

    @NotNull
    @Schema(description = "Фамилия автора")
    private String lastName;

    @Schema(description = "Отчество автора")
    private String middleName;

    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "СНИЛС")
    private String snils;

    @Schema(description = "Мобильный телефон")
    private String mobilePhone;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Трудоустройство")
    private Employment employment;

    @Schema(description = "ФИО автора в дательном падеже")
    private String fioDative;

    @Schema(description = "ФИО автора в родительном падеже")
    private String fioGenitive;

    @Schema(description = "ФИО автора в именительном падеже")
    private String fioNominative;

    @Schema(description = "Обращение автора")
    private AppealDto appeal;
}