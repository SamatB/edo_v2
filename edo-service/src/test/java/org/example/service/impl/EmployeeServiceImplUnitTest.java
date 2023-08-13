package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get employee by username")
    public void testGetEmployeeByUsername() {
        String username = "john.doe";
        Employee employee = new Employee();
        employee.setUsername(username);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setUsername(username);

        when(employeeRepository.findByUsername(username)).thenReturn(employee);
        when(employeeMapper.entityToDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());

        verify(employeeRepository, times(1)).findByUsername(username);
        verify(employeeMapper, times(1)).entityToDto(employee);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when employee not found by username")
    public void testGetEmployeeByUsernameNotFound() {
        String username = "john.doe";

        when(employeeRepository.findByUsername(username)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUsername(username));

        verify(employeeRepository, times(1)).findByUsername(username);
        verify(employeeMapper, never()).entityToDto(any());
    }

    @Test
    @DisplayName("Should get employee by id")
    public void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeMapper.entityToDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(employeeRepository, times(1)).findById(id);
        verify(employeeMapper, times(1)).entityToDto(employee);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when employee not found by id")
    public void testGetEmployeeByIdNotFound() {

        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(id));

        verify(employeeRepository, times(1)).findById(id);
        verify(employeeMapper, never()).entityToDto(any());
    }

}