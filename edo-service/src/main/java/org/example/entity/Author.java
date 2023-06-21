package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.utils.Employment;
import org.example.utils.EmploymentConverter;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

/**
 * Личные данные автора документа
 */
public class Author extends BaseEntity {
    /**
     * Имя автора
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;
    /**
     * Фамилия автора
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;
    /**
     * Отчество автора
     */
    @Column(name = "middle_name")
    private String middleName;
    /**
     * Адрес
     */
    @Column(name = "address")
    private String address;
    /**
     * СНИЛС
     */
    @Column(name = "snils", unique = true)
    private String snils;
    /**
     * Мобильный телефон
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;
    /**
     * Электронная почта
     */
    @Column(name = "email", unique = true)
    private String email;
    /**
     * Трудоустройство
     */
    @Column(name = "employment")
    @Convert(converter = EmploymentConverter.class)
    private Employment employment;
    /**
     * ФИО автора в дательном падеже
     */
    @Column(name = "fio_dative")
    private String fioDative;
    /**
     * ФИО автора в родительном падеже
     */
    @Column(name = "fio_genitive")
    private String fioGenitive;
    /**
     * ФИО автора в именительном падеже
     */
    @Column(name = "fio_nominative")
    private String fioNominative;
}
