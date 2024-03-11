package org.example.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/** Сервис отправки почты
*/
@Service
public interface EmailService {
    /** метод отправки почты
     */
    SimpleMailMessage sendEmail(String to);

    SimpleMailMessage sendAgreementListEmail(String to, String subject, String text);
}
