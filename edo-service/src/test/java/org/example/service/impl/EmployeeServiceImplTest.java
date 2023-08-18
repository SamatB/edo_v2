package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    @DisplayName("Should throw an exception when the id does not exist")
    void getEmployeeByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(id));

        verify(employeeRepository, times(1)).findById(id);
        verify(employeeMapper, never()).entityToDto(any(Employee.class));
    }

    @Test
    @DisplayName("Should return the employee when the id exists")
    void getEmployeeByIdWhenIdExists() {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeMapper.entityToDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeById(id);

        assertEquals(employeeDto, result);
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeMapper, times(1)).entityToDto(employee);
    }

    @Test
    @DisplayName("Should throw an exception when the username does not exist")
    void getEmployeeByUsernameWhenUsernameDoesNotExistThenThrowException() {
        String username = "john.doe";
        when(employeeRepository.findByUsername(username)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUsername(username));

        verify(employeeRepository, times(1)).findByUsername(username);
        verify(employeeMapper, never()).entityToDto(any());
    }

    @Test
    @DisplayName("Should return the employee when the username exists")
    void getEmployeeByUsernameWhenUsernameExists() {
        String username = "john.doe";
        Employee employee = new Employee();
        employee.setUsername(username);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setUsername(username);

        when(employeeRepository.findByUsername(username)).thenReturn(employee);
        when(employeeMapper.entityToDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeByUsername(username);

        assertEquals(employeeDto, result);
        verify(employeeRepository, times(1)).findByUsername(username);
        verify(employeeMapper, times(1)).entityToDto(employee);
    }
}