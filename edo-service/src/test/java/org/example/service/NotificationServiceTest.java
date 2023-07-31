package org.example.service;

import org.example.dto.NotificationDto;
import org.example.entity.Notification;
import org.example.mapper.NotificationMapper;
import org.example.repository.NotificationRepository;
import org.example.service.impl.NotificationServiceImpl;
import org.example.utils.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Класс для юнит-тестирования сервиса NotificationService.
 */
public class NotificationServiceTest {

    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    /**
     * Метод, который вызывается перед каждым тестом для инициализации тестового окружения.
     */
    @BeforeEach
    public void setUp() {
        notificationRepository = Mockito.mock(NotificationRepository.class);
        notificationMapper = Mockito.mock(NotificationMapper.class);
        notificationService = new NotificationServiceImpl(notificationRepository, notificationMapper);
    }

    /**
     * Тест для проверки корректности работы метода save в сервисе NotificationService.
     */
    @Test
    public void testSave() {
        // Подготовка данных для теста
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType(NotificationType.EMAIL);

        Notification notification = new Notification();
        notification.setType(NotificationType.EMAIL);

        when(notificationMapper.dtoToEntity(notificationDto)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.entityToDto(notification)).thenReturn(notificationDto);

        // Вызов тестируемого метода
        NotificationDto savedNotificationDto = notificationService.save(notificationDto);

        // Проверка результатов
        assertEquals(NotificationType.EMAIL, savedNotificationDto.getType());

        verify(notificationMapper).dtoToEntity(notificationDto);
        verify(notificationRepository).save(any(Notification.class));
        verify(notificationMapper).entityToDto(notification);
    }
}
