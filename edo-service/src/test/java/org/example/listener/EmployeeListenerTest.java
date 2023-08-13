package org.example.listener;

import org.example.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeListener")
class EmployeeListenerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeListener employeeListener;

    @Test
    @DisplayName("Should log the receipt and successful saving of an employee")
    void receiveEmployeeLogsReceiptAndSaving() {
        EmployeeDto employeeDto = new EmployeeDto();

        when(employeeService.saveEmployee(employeeDto)).thenReturn(employeeDto);

        employeeListener.receiveEmployee(employeeDto);


        verify(employeeService, times(1)).saveEmployee(employeeDto);
    }

}