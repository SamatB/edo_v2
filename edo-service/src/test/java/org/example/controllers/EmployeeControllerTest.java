package org.example.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.example.configuration.RabbitConfiguration;
import org.example.controller.EmployeeController;
import org.example.dto.EmailDto;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    @DisplayName("Should successfully send EmailDto and return OK status")
    void sendEmailDtoSuccessfully() {
        EmailDto emailDto = new EmailDto(); // Assume EmailDto is correctly instantiated and populated

        ResponseEntity<String> response = employeeController.sendEmailDTO(emailDto);

        verify(rabbitTemplate).convertAndSend(RabbitConfiguration.GET_EMAIL_DTO, emailDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody());
    }

    @Test
    @DisplayName("Should return INTERNAL_SERVER_ERROR status when an exception occurs")
    void sendEmailDtoWithException() {
        EmailDto emailDto = new EmailDto(); // Assume EmailDto is correctly instantiated and populated
        doThrow(new RuntimeException("Test exception")).when(rabbitTemplate).convertAndSend(RabbitConfiguration.GET_EMAIL_DTO, emailDto);

        ResponseEntity<String> response = employeeController.sendEmailDTO(emailDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("emailDto не добавилась в очередь", response.getBody());
    }
}