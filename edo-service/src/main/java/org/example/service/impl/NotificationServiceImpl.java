package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.NotificationDto;
import org.example.entity.Notification;
import org.example.mapper.NotificationMapper;
import org.example.repository.NotificationRepository;
import org.example.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Сервис для работы с сущностью Notification.
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    /**
     * Сохраняет оповещение в базе данных.
     *
     * @param notificationDto объект DTO оповещения
     * @return сохраненный объект DTO оповещения
     */
    @Override
    public NotificationDto save(NotificationDto notificationDto) {
        Notification notification = notificationMapper.dtoToEntity(notificationDto);
        notification.setCreationDate(ZonedDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.entityToDto(savedNotification);
    }
}
