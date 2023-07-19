package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.NotificationDto;
import org.example.entity.Notification;
import org.example.mapper.NotificationMapper;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Сервис для работы с сущностью Notification.
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    /**
     * Сохраняет оповещение в базе данных.
     *
     * @param notificationDto объект DTO оповещения
     * @return сохраненный объект DTO оповещения
     */
    public NotificationDto save(NotificationDto notificationDto) {
        Notification notification = notificationMapper.dtoToEntity(notificationDto);
        notification.setCreationDate(ZonedDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.entityToDto(savedNotification);
    }
}
