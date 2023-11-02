package org.example.repository.mapper;

import org.example.dto.NotificationDto;
import org.example.entity.Notification;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью Notification и объектом NotificationDto.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends AbstractMapper<Notification, NotificationDto> {
}
