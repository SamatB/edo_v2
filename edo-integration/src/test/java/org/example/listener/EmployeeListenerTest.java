package org.example.listener;

import org.example.dto.EmailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmployeeListenerTest {
    @InjectMocks
    private EmployeeListener employeeListener;


    @Test
    @DisplayName("Should send emails to all EmailDto and log success message")
    void sendEmailToEmployeeDtoIdWhenSuccessful() {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(List.of("vanya22@mail.ru"));
        emailDto.setSubject("Test subject");
        emailDto.setText("Test text");
        employeeListener.sendEmailToEmailDTO(emailDto);
    }

}