package org.example.service.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmailService;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;
    /**
     * метод для отправки почты с помощью Spring Mail API
     * @param to получатель email.
     * @return SimpleMailMessage модель письма для отправки.
     */
    @Override
    public SimpleMailMessage sendEmail(String to) {
        if (to == null) {
            throw new IllegalArgumentException("Отправитель не может быть null");
        }
        SimpleMailMessage message = new SimpleMailMessage() {{
            setFrom("john.doe@example.org");
            setTo(to);
            setSubject("Hello world");
            setText("Hello");
        }};
        try {
            emailSender.send(message);
            log.info("Письмо было отправлено на адрес: " + to);
        } catch (MailSendException e) {
            log.warn("Отправка не произошла: " + e.getMessage());
        }
        return message;
    }


    /**
     * метод для отправки почты с помощью Spring Mail API
     * @param to получатель email
     * @param subject тема письма
     * @param text текст письма
     * @return SimpleMailMessage модель письма для отправки.
     */
    @Override
    public SimpleMailMessage sendAgreementListEmail(String to, String subject, String text) {
        if (to == null) {
            throw new IllegalArgumentException("Отправитель не может быть null");
        }
        SimpleMailMessage message = new SimpleMailMessage() {{
            setFrom("john.doe@example.org");
            setTo(to);
            setSubject(subject);
            setText(text);
        }};
        try {
            emailSender.send(message);
            log.info("Письмо было отправлено на адрес: " + to);
        } catch (MailSendException e) {
            log.warn("Отправка не произошла: " + e.getMessage());
        }
        return message;
    }
}
