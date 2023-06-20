package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO-класс для передачи информации об адресе.
 */
@Data
@Schema(description = "Адрес")
public class AddressDto {
    @Schema(description = "Полный адрес")
    String fullAddress;
    @Schema(description = "Название улицы")
    String street;
    @Schema(description = "Номер дома")
    String house;
    @Schema(description = "Почтовый индекс")
    String index;
    @Schema(description = "Корпус здания")
    String housing;
    @Schema(description = "Строение здания")
    String building;
    @Schema(description = "Город")
    String city;
    @Schema(description = "Регион или область")
    String region;
    @Schema(description = "Страна")
    String country;
    @Schema(description = "Номер квартиры")
    String flat;
}