package org.example.mapper;

import org.example.dto.AddressDto;
import org.example.entity.Address;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends AbstractMapper<Address, AddressDto> {
}
