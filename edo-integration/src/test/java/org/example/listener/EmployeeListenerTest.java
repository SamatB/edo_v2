package org.example.listener;

import org.example.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeListenerTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeListener employeeListener;



    @Test
    @DisplayName("Should log error message when exception is thrown during email sending")
    void sendEmailToEmployeeDtoIdWhenExceptionThrown() {
        Collection<Long> employeeDtoId = Arrays.asList(1L, 2L, 3L);
        when(employeeService.getEmailById(employeeDtoId)).thenThrow(new RuntimeException("Error retrieving email"));
        employeeListener.sendEmailToEmployeeDtoId(employeeDtoId);
    }

    @Test
    @DisplayName("Should send emails to all employeeDtoId and log success message")
    void sendEmailToEmployeeDtoIdWhenSuccessful() {
        Collection<Long> employeeDtoIds = Arrays.asList(1L, 2L, 3L);
        when(employeeService.getEmailById(employeeDtoIds)).thenReturn(Arrays.asList("email1@example.com", "email2@example.com", "email3@example.com"));
        employeeListener.sendEmailToEmployeeDtoId(employeeDtoIds);
    }

}