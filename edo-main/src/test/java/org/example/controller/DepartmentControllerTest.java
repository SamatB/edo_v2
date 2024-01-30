//package org.example.controller;
//
//import org.example.dto.DepartmentDto;
//import org.example.feign.DepartmentFeignClient;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
///**
// * Тесты для класса DepartmentController.
// */
//
//@DisplayName("Тестирование контроллера для работы с департаментом")
//public class DepartmentControllerTest {
//
//    @Mock
//    private DepartmentFeignClient departmentFeignClient;
//
//    @InjectMocks
//    private DepartmentController departmentController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    /**
//     * Тест для метода getDepartmentByName.
//     */
//
//    @Test
//    public void getDepartmentByName_shouldPositive() {
//        DepartmentDto departmentDto = new DepartmentDto();
//        List<DepartmentDto> departmentDtoList = new ArrayList<>();
//
//        departmentDtoList.add(departmentDto);
//
//        when(departmentFeignClient.getDepartmentByName(anyString()).getBody()).thenReturn(departmentDtoList);
//
//        ResponseEntity<List<DepartmentDto>> response = departmentController.getDepartmentByName("мэрия");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(departmentDtoList, response.getBody());
//    }
//
//
//    /**
//     * Тест для метода getDepartmentByName, если департамент не найден.
//     */
//
//    @Test
//    public void getDepartmentByName_shouldNotFound() {
//        List<DepartmentDto> departmentDtoList = new ArrayList<>();
//
//        when(departmentFeignClient.getDepartmentByName(anyString()).getBody()).thenReturn(departmentDtoList);
//
//        ResponseEntity<List<DepartmentDto>> response = departmentController.getDepartmentByName("мэрия");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(departmentDtoList, response.getBody());
//    }
//}
