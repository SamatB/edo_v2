package org.example.service.impl;

import org.example.dto.AddressDto;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование сервиса для работы с работниками")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private Logger log;

    @Test
    @DisplayName("Should save the address details of the saved employee")
    void saveEmployeeAndSaveAddressDetails() {        // Create a sample EmployeeDto object
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .addressDetails(AddressDto.builder()
                        .fullAddress("123 Main St, City, State, Country")
                        .street("123 Main St")
                        .house("123")
                        .index("12345")
                        .city("City")
                        .region("State")
                        .country("Country")
                        .build())
                .build();

        // Create a sample Employee object
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .addressDetails(new Address("123 Main St, City, State, Country", "123 Main St", "123", "12345", null, null, "City", "State", "Country", null))
                .build();

        // Create a sample AddressDto object
        AddressDto addressDto = AddressDto.builder()
                .fullAddress("123 Main St, City, State, Country")
                .street("123 Main St")
                .house("123")
                .index("12345")
                .city("City")
                .region("State")
                .country("Country")
                .build();

        // Mock the behavior of the employeeMapper and addressService
        when(employeeMapper.dtoToEntity(employeeDto)).thenReturn(employee);
        when(addressService.saveAddress(employeeDto.getAddressDetails())).thenReturn(addressDto);

        // Call the saveEmployee method
        EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);

        // Verify that the employeeRepository.save method was called with the correct employee object
        verify(employeeRepository, times(1)).save(employee);

        // Verify that the addressService.saveAddress method was called with the correct addressDto object
        verify(addressService, times(1)).saveAddress(employeeDto.getAddressDetails());

        // Verify that the employeeMapper.entityToDto method was called with the correct employee object
        verify(employeeMapper, times(1)).entityToDto(employee);

        // Verify that the returned EmployeeDto object matches the expected result
        assertEquals(savedEmployeeDto, employeeDto);
    }

    @Test
    @DisplayName("Should log the address details of the saved employee")
    void saveEmployeeAndLogAddressDetails() {        // Create a mock EmployeeDto object
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .addressDetails(AddressDto.builder()
                        .fullAddress("123 Main St, City, State")
                        .street("123 Main St")
                        .city("City")
                        .region("State")
                        .build())
                .build();

        // Create a mock Employee object
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .addressDetails(new Address("123 Main St, City, State", "123 Main St", null, null, null, null, "City", "State", null, null))
                .build();

        // Create a mock AddressDto object
        AddressDto addressDto = AddressDto.builder()
                .fullAddress("123 Main St, City, State")
                .street("123 Main St")
                .city("City")
                .region("State")
                .build();

        // Mock the behavior of the employeeMapper
        when(employeeMapper.dtoToEntity(employeeDto)).thenReturn(employee);
        when(addressService.saveAddress(employeeDto.getAddressDetails())).thenReturn(addressDto);

        // Call the saveEmployee method
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

        // Verify that the employeeRepository.save method was called with the correct employee object
        verify(employeeRepository, times(1)).save(employee);

        // Verify that the addressService.saveAddress method was called with the correct addressDto object
        verify(addressService, times(1)).saveAddress(employeeDto.getAddressDetails());

        // Verify that the employeeMapper.entityToDto method was called with the correct employee object
        verify(employeeMapper, times(1)).entityToDto(employee);

        // Verify that the log.info method was called with the correct address details
        verify(log, times(1)).info("Получили адрессдетэйлз! {} ", addressDto);

        // Assert that the returned EmployeeDto object is the same as the mocked EmployeeDto object
        assertEquals(employeeDto, savedEmployee);
    }

    @Test
    @DisplayName("Should save the employee and return the saved employee's DTO")
    void saveEmployeeAndReturnSavedEmployeeDto() {        // Create a mock EmployeeDto object
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .build();

        // Create a mock Employee object
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .build();

        // Create a mock AddressDto object
        AddressDto addressDto = AddressDto.builder()
                .fullAddress("123 Main St")
                .build();

        // Create a mock Address object
        Address address = Address.builder()
                .fullAddress("123 Main St")
                .build();

        // Mock the behavior of the employeeMapper
        when(employeeMapper.dtoToEntity(employeeDto)).thenReturn(employee);
        when(employeeMapper.entityToDto(employee)).thenReturn(employeeDto);

        // Mock the behavior of the addressService
        when(addressService.saveAddress(addressDto)).thenReturn(addressDto);

        // Mock the behavior of the employeeRepository
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Call the method under test
        EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);

        // Verify the interactions
        verify(employeeMapper, times(1)).dtoToEntity(employeeDto);
        verify(employeeMapper, times(1)).entityToDto(employee);
        verify(addressService, times(1)).saveAddress(addressDto);
        verify(employeeRepository, times(1)).save(employee);

        // Assert the result
        assertNotNull(savedEmployeeDto);
        assertEquals(employeeDto.getFirstName(), savedEmployeeDto.getFirstName());
        assertEquals(employeeDto.getLastName(), savedEmployeeDto.getLastName());
        assertEquals(employeeDto.getAddress(), savedEmployeeDto.getAddress());
    }

}