package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.dto.EmployeeDto;
import org.example.entity.Address;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.service.AddressService;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AddressService addressService;

    private static EmployeeDto getEmployeeDto() {
        AddressDto address = new AddressDto();
        address.setCity("City");
        address.setStreet("Street");
        address.setHouse("House");
        address.setFlat("Flat");
        address.setFullAddress("FullAddress");

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setFullName("DepartmentFullName");
        departmentDto.setAddress("address");
        departmentDto.setAddressDetails(address);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFioNominative("EmployeeFioNominative");
        employeeDto.setEmail("EmployeeEmail");
        employeeDto.setPhone("EmployeePhone");
        employeeDto.setUsername("EmployeeUsername");
        employeeDto.setAddress("EmployeeAddress");
        employeeDto.setDepartment(departmentDto);
        employeeDto.setAddressDetails(address);
        return employeeDto;
    }

    private static Employee getEmployee() {
        Address address = new Address();
        address.setCity("City");
        address.setStreet("Street");
        address.setHouse("House");
        address.setFlat("Flat");
        address.setFullAddress("FullAddress");

        Department department = new Department();
        department.setId(1L);
        department.setFullName("DepartmentFullName");
        department.setAddress("address");
        department.setAddressDetails(address);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFioNominative("EmployeeFioNominative");
        employee.setEmail("EmployeeEmail");
        employee.setPhone("EmployeePhone");
        employee.setUsername("EmployeeUsername");
        employee.setAddress("EmployeeAddress");
        employee.setAddressDetails(address);
        employee.setDepartment(department);
        return employee;
    }

    @Test
    @DisplayName("Should correctly map EmployeeDto to Employee")
    void employeeDtoToEmployee() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();

        Employee result = employeeMapper.dtoToEntity(employeeDto);

        assertNotNull(result.getAddressDetails());
        assertNotNull(result.getDepartment());
        assertNotNull(result.getDepartment().getAddressDetails());
        assertEquals(employee, result);
        assertEquals(employee.getAddressDetails().getFullAddress(), result.getAddressDetails().getFullAddress());
        assertEquals(employee.getDepartment().getAddressDetails().getFullAddress(), result.getDepartment().getAddressDetails().getFullAddress());
    }

    @Test
    @DisplayName("Should correctly map Employee to EmployeeDto")
    void employeeToEmployeeDto() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();

        EmployeeDto result = employeeMapper.entityToDto(employee);
        log.info("Address: {}", result.getAddressDetails());
        log.info("Department: {}", result.getDepartment());
        assertNotNull(result.getAddressDetails());
        assertNotNull(result.getDepartment());
        assertNotNull(result.getDepartment().getAddressDetails());
        assertEquals(employeeDto, result);
        assertEquals(employee.getAddressDetails().getFullAddress(), result.getAddressDetails().getFullAddress());
//        assertEquals(employee.getDepartment().getAddressDetails().getFullAddress(), result.getDepartment().getAddressDetails().getFullAddress());
    }
}