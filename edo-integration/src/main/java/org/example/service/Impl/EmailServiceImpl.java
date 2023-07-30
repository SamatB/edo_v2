package org.example.service.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;
    private final String FROM = "john.doe@example.org";

    @Override
    public void sendEmail(String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM);
            message.setTo(to);
            message.setSubject("Hello world");
            message.setText("Hello");
            emailSender.send(message);
            log.info("Письмо было отправлено на адрес: " + to);
        } catch (Exception e) {
            System.out.println("Отправка не произошла: " + e.getMessage());
        }
    }
}
