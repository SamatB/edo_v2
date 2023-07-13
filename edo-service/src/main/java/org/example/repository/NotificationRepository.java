package org.example.repository;

import org.example.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Notification.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
