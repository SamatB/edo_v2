package org.example.service.Impl;

import org.example.dto.EmailDto;
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
    @DisplayName("Should throw an exception when the recipient is null")
    void sendEmailWhenRecipientIsNullThenThrowException() {
        EmailDto emailDto = new EmailDto();
        emailDto.setSubject("Hello world");
        emailDto.setText("Hello");
        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(emailDto));
    }
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

    @Test
    @DisplayName("Should throw an exception when the EmailDto is null")
    void sendEmailWhenEmailDtoIsNullThenThrowException() {
        EmailDto emailDto = new EmailDto();
        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(emailDto));
    }

    @Test
    @DisplayName("Should catch and log the exception when the email sending fails")
    void sendEmailDtoWhenEmailSendingFailsThenCatchAndLogException() {
        EmailDto emailDto = new EmailDto();
        emailDto.setTo("test@example.com");
        emailDto.setFrom("john.doe@example.org");
        emailDto.setSubject("Hello world");
        emailDto.setText("Hello");
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom(emailDto.getFrom());
        expectedMessage.setTo(emailDto.getTo());
        expectedMessage.setSubject(emailDto.getSubject());
        expectedMessage.setText(emailDto.getText());

        doThrow(new MailSendException("Failed to send email")).when(emailSender).send(expectedMessage);

        SimpleMailMessage actualMessage = emailService.sendEmail(emailDto);

        verify(emailSender, times(1)).send(expectedMessage);
        assertEquals(expectedMessage, actualMessage);
    }
}