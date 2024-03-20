package org.example.service;

import org.example.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/** Сервис отправки почты
*/
@Service
public interface EmailService {
    /***
     * Методы для отправки почты
     * @param to принимает почту, на которое должно прийти сообщение
     * @param subject принимает тему для сообщения
     * @param text принимает текс для сообщения
     */

    SimpleMailMessage sendEmail(EmailDto emailDto);
    SimpleMailMessage sendEmail(String to, String subject, String text);
}
