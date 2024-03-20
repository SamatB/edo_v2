package org.example.service.Impl;

import org.example.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailServiceImpl emailService;


    @Test
    @DisplayName("Should catch and log the exception when the email sending fails")
    void sendEmailWhenEmailSendingFailsThenCatchAndLogException() {
        String to = "test@yandex.ru";
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("edo.2.kate@gmail.com");
        expectedMessage.setTo(to);
        expectedMessage.setSubject("Hello world");
        expectedMessage.setText("Hello");

        SimpleMailMessage actualMessage = emailService.sendEmail(to, "Hello world", "Hello");

        verify(emailSender, times(1)).send(expectedMessage);
        assertEquals(expectedMessage, actualMessage);
    }



}