package org.example.listener;

import org.example.dto.AppealDto;
import org.example.dto.EmployeeDto;
import org.example.entity.Appeal;
import org.example.listener.EmployeeListener;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EmployeeListenerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeListener employeeListener;

    private EmployeeDto employeeDto;

    private EmployeeMapper employeeMapper;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест получения DTO работника из очереди.
     */
    @Test
    public void testReceiveEmployee_Successful() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeListener.receiveEmployee(employeeDto);
        verify(employeeService).saveEmployee(employeeDto);
    }


}