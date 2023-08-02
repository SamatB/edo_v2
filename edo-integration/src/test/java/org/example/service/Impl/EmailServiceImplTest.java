package org.example.service.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(null));
    }
    @Test
    @DisplayName("Should catch and log the exception when the email sending fails")
    void sendEmailWhenEmailSendingFailsThenCatchAndLogException() {
        String to = "test@example.com";
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("john.doe@example.org");
        expectedMessage.setTo(to);
        expectedMessage.setSubject("Hello world");
        expectedMessage.setText("Hello");

        doThrow(new MailSendException("Failed to send email")).when(emailSender).send(expectedMessage);

        SimpleMailMessage actualMessage = emailService.sendEmail(to);

        verify(emailSender, times(1)).send(expectedMessage);
        assertEquals(expectedMessage, actualMessage);
    }

}