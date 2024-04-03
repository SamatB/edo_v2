package org.example.service;

import org.example.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Сервис отправки почты
 */
@Service
public interface EmailService {
    /***
     * Метод для отправки почты
     * @param to принимает почту, на которое должно прийти сообщение
     * @param subject принимает тему для сообщения
     * @param text принимает текс для сообщения
     */
    SimpleMailMessage sendEmail(String to, String subject, String text);

    /**
     * Метод для отправки почты с помощью Spring Mail API
     *
     * @param emailDto принимает объект DTO с данными для отправки
     */
    SimpleMailMessage sendEmail(EmailDto emailDto);
}
