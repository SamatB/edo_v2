package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO-класс для передачи информации об адресе.
 */
@Data
@Schema(description = "Адрес")
public class AddressDto implements Serializable {

    @Schema(description = "идентификатор адреса")
    private Long id;
    @Schema(description = "Полный адрес")
    private String fullAddress;
    @Schema(description = "Название улицы")
    private String street;
    @Schema(description = "Номер дома")
    private String house;
    @Schema(description = "Почтовый индекс")
    private String index;
    @Schema(description = "Корпус здания")
    private String housing;
    @Schema(description = "Строение здания")
    private String building;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Регион или область")
    private String region;
    @Schema(description = "Страна")
    private String country;
    @Schema(description = "Номер квартиры")
    private String flat;
}