package org.example.listener;

import org.example.dto.EmployeeDto;
import org.example.listener.EmployeeListener;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class EmployeeListenerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeListener employeeListener;

    @BeforeEach
    public void setUp() {
        // Инициализация mock объектов перед каждым тестом
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveEmployee_Successful() {
        // Test 1: Успешное получение работника из очереди
        // Arrange
        EmployeeDto employeeDto = new EmployeeDto();

        // Act
        employeeListener.receiveEmployee(employeeDto);

        // Assert
        verify(employeeService).saveEmployee(employeeDto);
    }

}