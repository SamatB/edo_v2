package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.entity.Address;
import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;
import org.example.repository.DepartmentRepository;
import org.example.service.impl.AddressServiceImpl;
import org.example.service.impl.DepartmentServiceImpl;
import org.example.utils.CheckingLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса DepartmentService.
 */
class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    AddressServiceImpl addressService;

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
        Address address = new Address();
        Department departmentMock = mock(Department.class);
        when(departmentMapper.dtoToEntity(departmentDto)).thenReturn(departmentMock);
        when(addressService.saveAddress(address)).thenReturn(address);
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
        Address address = new Address();
        DepartmentDto parentDepartmentDto = new DepartmentDto();
        departmentDto.setDepartment(parentDepartmentDto);
        AddressDto addressDto = new AddressDto();
        departmentDto.setAddressDetails(addressDto);

        Department departmentMock = mock(Department.class);
        when(departmentMapper.dtoToEntity(departmentDto)).thenReturn(departmentMock);
        when(addressService.saveAddress(address)).thenReturn(address);
        when(departmentRepository.save(departmentMock)).thenReturn(departmentMock);
        when(departmentMapper.entityToDto(departmentMock)).thenReturn(departmentDto);

        DepartmentDto result = departmentService.saveDepartment(departmentDto);
        assertEquals(departmentDto, result);
    }

    /**
     * Тест для метода fixLayout с русской расскладкой.
     */

    @Test
    void fixLayout_should_Russian_layout() {
        String resault = CheckingLayout.fixLayout("мэрия");

        assertEquals("мэрия", resault);
    }

    /**
     * Тест для метода fixLayout с английской расскладкой.
     */

    @Test
    void fixLayout_should_English_layout() {
        String resault = CheckingLayout.fixLayout("v'hbz");

        assertEquals("мэрия", resault);
    }

    /**
     * Тест для метода getDepartmentByName, если пользователь существует в БД.
     */
    @Test
    void getDepartmentByName_ThereIsUser() {
        String fullName = "мэрия";
        List<Department> departmentList = new ArrayList<>();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        Department department = new Department();
        department.setFullName(fullName);
        departmentList.add(department);

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setFullName(fullName);
        departmentDtoList.add(departmentDto);

        when(departmentRepository.searchByName(fullName)).thenReturn(departmentList);
        when(departmentMapper.entityListToDtoList(departmentList)).thenReturn(departmentDtoList);

        List<DepartmentDto> resault = departmentService.getDepartmentByName(fullName);
        assertEquals(departmentDtoList, resault);

    }

    /**
     * Тест для метода getDepartmentByName, если пользователь не существует в БД.
     */
    @Test
    void getDepartmentByName_ThereIsNotUser() {
        String fullName = "мэрия";
        List<Department> departmentList = new ArrayList<>();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        when(departmentRepository.searchByName(fullName)).thenReturn(departmentList);

        List<DepartmentDto> resault = departmentService.getDepartmentByName(fullName);
        assertEquals(departmentDtoList, resault);
    }

    /**
     * Тест для метода getDepartmentByName, при строке поиска менее трех символов.
     */

    @Test
    void getDepartmentByName_LessThanThreeCharacters() {
        assertThrows(EntityNotFoundException.class, () -> departmentService.getDepartmentByName("мэ"));
    }

}