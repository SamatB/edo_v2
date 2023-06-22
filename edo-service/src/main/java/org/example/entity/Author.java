package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    /**
     * Фамилия автора
     */
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;
    /**
     * Отчество автора
     */
    @Column(name = "middle_name", length = 255)
    private String middleName;
    /**
     * Адрес
     */
    @Column(name = "address", length = 255)
    private String address;
    /**
     * СНИЛС
     */
    @Column(name = "snils", unique = true, length = 255)
    private String snils;
    /**
     * Мобильный телефон
     */
    @Column(name = "mobile_phone", length = 255)
    private String mobilePhone;
    /**
     * Электронная почта
     */
    @Column(name = "email", unique = true, length = 255)
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
    @Column(name = "fio_dative", length = 255)
    private String fioDative;
    /**
     * ФИО автора в родительном падеже
     */
    @Column(name = "fio_genitive", length = 255)
    private String fioGenitive;
    /**
     * ФИО автора в именительном падеже
     */
    @Column(name = "fio_nominative", length = 255)
    private String fioNominative;
}
