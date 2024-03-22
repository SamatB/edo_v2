package org.example.mapper;

import org.example.dto.ParticipantDto;
import org.example.entity.Participant;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер преобразует Participant в ParticipantDto и обратно
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ParticipantMapper extends AbstractMapper<Participant, ParticipantDto> {
    /**
     * Эти 2 метода нужны как костыль пока MapStruct не починит проблему с использованием других мапперов
     * На момент их добавления не работает @Autowire для EmployeeMapper
     * Без них поля с типом Employee и EmployeeDto не маппятся и становятся null
     */
    @Override
    @Mapping(source = "employee.addressDetails", target = "employeeDto.addressDetails")
    ParticipantDto entityToDto(Participant participant);

    @Override
    @Mapping(source = "employeeDto.addressDetails", target = "employee.addressDetails")
    Participant dtoToEntity(ParticipantDto participantDto);
}
