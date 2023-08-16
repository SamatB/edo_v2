package org.example.controllers;

import org.example.service.RabbitmqSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeController")
class EmployeeControllerTest {

    @Mock
    private RabbitmqSender sender;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    @DisplayName("Should return INTERNAL_SERVER_ERROR status when an exception occurs")
    void sendEmployeeDtoIdWhenExceptionOccurs() {
        Collection<Long> idEmployeeDTO = List.of(1L, 2L, 44L);
        doThrow(new RuntimeException("Error sending employeeDtoId")).when(sender).send("employeeDtoId", idEmployeeDTO);
        ResponseEntity<String> response = employeeController.sendEmployeeDtoId(idEmployeeDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(sender, times(1)).send("employeeDtoId", idEmployeeDTO);
    }

    @Test
    @DisplayName("Should send the collection of EmployeeDTO IDs to the queue and return status OK")
    void sendEmployeeDtoIdWhenSuccessful() {
        Collection<Long> idEmployeeDTO = List.of(1L, 2L, 55L);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("ok");
        ResponseEntity<String> actualResponse = employeeController.sendEmployeeDtoId(idEmployeeDTO);
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

}