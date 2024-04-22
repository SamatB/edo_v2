package org.example.mapper;

import org.example.dto.NotificationDto;
import org.example.entity.Notification;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью Notification и объектом NotificationDto.
 */
@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface NotificationMapper extends AbstractMapper<Notification, NotificationDto> {

    @Override
    @Mapping(source = "employee.id", target = "employeeId")
    NotificationDto entityToDto(Notification notification);

    @Override
    @Mapping(source = "employeeId", target = "employee.id")
    Notification dtoToEntity(NotificationDto notificationDto);
}
