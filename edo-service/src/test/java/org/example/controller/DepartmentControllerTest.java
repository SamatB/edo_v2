package org.example.controller;

import org.example.dto.DepartmentDto;

import org.example.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Тесты для класса DepartmentController.
 */

@DisplayName("Тестирование контроллера для работы с департаментом")
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода getDepartmentByName.
     */

    @Test
    public void getDepartmentByName_shouldPositive() {
        DepartmentDto departmentDto = new DepartmentDto();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        departmentDtoList.add(departmentDto);

        when(departmentService.getDepartmentByName(anyString())).thenReturn(departmentDtoList);

        ResponseEntity<List<DepartmentDto>> response = departmentController.getDepartmentByName("мэрия");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDtoList, response.getBody());
    }


    /**
     * Тест для метода getDepartmentByName, если департамент не найден.
     */

    @Test
    public void getDepartmentByName_shouldNotFound() {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        when(departmentService.getDepartmentByName(anyString())).thenReturn(departmentDtoList);

        ResponseEntity<List<DepartmentDto>> response = departmentController.getDepartmentByName("мэрия");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDtoList, response.getBody());
    }
}
