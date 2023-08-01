package org.example.service;

import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;
import org.example.repository.DepartmentRepository;
import org.example.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса DepartmentService.
 */
class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    DepartmentMapper departmentMapper;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода saveDepartment без вышестоящего департамента и адреса.
     */
    @Test
    void saveDepartment_NoParentDepartment_NoAddressDetails() {
        DepartmentDto departmentDto = new DepartmentDto();
        Department departmentMock = mock(Department.class);
        when(departmentMapper.dtoToEntity(departmentDto)).thenReturn(departmentMock);
        when(departmentRepository.save(departmentMock)).thenReturn(departmentMock);
        when(departmentMapper.entityToDto(departmentMock)).thenReturn(departmentDto);

        DepartmentDto result = departmentService.saveDepartment(departmentDto);
        assertEquals(departmentDto, result);
    }

    /**
     * Тест для метода saveDepartment с вышестоящим департаментом и адресом.
     */
    @Test
    void saveDepartment_WithParentDepartment_WithAddressDetails() {
        DepartmentDto departmentDto = new DepartmentDto();
        DepartmentDto parentDepartmentDto = new DepartmentDto();
        departmentDto.setDepartment(parentDepartmentDto);
        AddressDto addressDto = new AddressDto();
        departmentDto.setAddressDetails(addressDto);

        Department departmentMock = mock(Department.class);
        when(departmentMapper.dtoToEntity(departmentDto)).thenReturn(departmentMock);
        when(departmentRepository.save(departmentMock)).thenReturn(departmentMock);
        when(departmentMapper.entityToDto(departmentMock)).thenReturn(departmentDto);

        DepartmentDto result = departmentService.saveDepartment(departmentDto);
        assertEquals(departmentDto, result);
    }
}