package org.example.repository.mapper;

import org.example.dto.AddressDto;
import org.example.entity.Address;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Address и объектом AddressDto.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends AbstractMapper<Address, AddressDto> {
}
