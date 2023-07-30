package org.example.service;

import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода saveEmployee.
     */
    @Test
    public void testSaveEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        Employee employeeMock = mock(Employee.class);
        when(employeeMapper.dtoToEntity(employeeDto)).thenReturn(employeeMock);
        when(employeeRepository.save(employeeMock)).thenReturn(employeeMock);
        when(employeeMapper.entityToDto(employeeMock)).thenReturn(employeeDto);

        EmployeeDto test = employeeService.saveEmployee(employeeDto);
        assertEquals(employeeDto, test);
    }
}
