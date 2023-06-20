package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Класс, представляющий адрес.
 */
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Address extends BaseEntity {
    /**
     * Полный адрес.
     */
    @Column(name = "full_address", length = 500)
    private String fullAddress;
    /**
     * Название улицы.
     */
    @Column(name = "street", length = 50)
    private String street;
    /**
     * Номер дома.
     */
    @Column(name = "house", length = 50)
    private String house;
    /**
     * Почтовый индекс.
     */
    @Column(name = "postcode", length = 50)
    private String index;
    /**
     * Корпус здания.
     */
    @Column(name = "housing", length = 50)
    private String housing;
    /**
     * Строение здания.
     */
    @Column(name = "building", length = 50)
    private String building;
    /**
     * Город.
     */
    @Column(name = "city", length = 50)
    private String city;
    /**
     * Регион или область.
     */
    @Column(name = "region", length = 50)
    private String region;
    /**
     * Страна.
     */
    @Column(name = "country", length = 50)
    private String country;
    /**
     * Номер квартиры.
     */
    @Column(name = "flat", length = 50)
    private String flat;
}