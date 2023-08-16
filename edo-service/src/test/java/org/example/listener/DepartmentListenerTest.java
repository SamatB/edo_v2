package org.example.listener;

import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

/**
 * Тесты для класса DepartmentListener.
 */
class DepartmentListenerTest {

    @Mock
    private DepartmentServiceImpl departmentService;

    @InjectMocks
    private DepartmentListener departmentListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода receiveDepartment.
     * Проверка корректности преобразования JSON в объект DepartmentDto
     */
    @Test
    void receiveDepartment_ValidJson_ShouldCallSaveDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setFullName("Отдел разработки");
        departmentDto.setShortName("Разраб.");
        departmentDto.setAddress("ул. Примерная, 123");
        AddressDto addressDetails = new AddressDto();
        addressDetails.setFullAddress("ул. Примерная, 123, корпус 1, строение 2, кв. 45");
        addressDetails.setStreet("ул. Примерная");
        addressDetails.setHouse("123");
        addressDetails.setIndex("123456");
        addressDetails.setHousing("корпус 1");
        addressDetails.setBuilding("строение 2");
        addressDetails.setCity("Город");
        addressDetails.setRegion("Область");
        addressDetails.setCountry("Страна");
        addressDetails.setFlat("45");
        departmentDto.setAddressDetails(addressDetails);

        departmentDto.setPhone("+12345");
        departmentDto.setExternalId("EXT-001");
        DepartmentDto nestedDepartmentDto = new DepartmentDto();
        nestedDepartmentDto.setId(2L);
        nestedDepartmentDto.setFullName("Отдел тестирования");
        nestedDepartmentDto.setShortName("Тест.");
        nestedDepartmentDto.setAddress("ул. Тестовая, 456");
        AddressDto nestedAddressDetails = new AddressDto();
        nestedAddressDetails.setFullAddress("ул. Примерная, 130, корпус 1, строение 2, кв. 45");
        nestedAddressDetails.setStreet("ул. Примерная");
        nestedAddressDetails.setHouse("130");
        nestedAddressDetails.setIndex("123456");
        nestedAddressDetails.setHousing("корпус 1");
        nestedAddressDetails.setBuilding("строение 2");
        nestedAddressDetails.setCity("Город");
        nestedAddressDetails.setRegion("Область");
        nestedAddressDetails.setCountry("Страна");
        nestedAddressDetails.setFlat("45");
        nestedDepartmentDto.setAddressDetails(nestedAddressDetails);

        nestedDepartmentDto.setPhone("+98765");
        nestedDepartmentDto.setExternalId("EXT-002");
        nestedDepartmentDto.setCreationDate(ZonedDateTime.parse("2023-07-22T12:34:56+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        nestedDepartmentDto.setArchivedDate(null);
        departmentDto.setDepartment(nestedDepartmentDto);

        departmentDto.setCreationDate(ZonedDateTime.parse("2023-07-22T12:34:56+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        departmentDto.setArchivedDate(null);

        when(departmentService.saveDepartment(departmentDto)).thenReturn(departmentDto);
        departmentListener.receiveDepartment(departmentDto);
        verify(departmentService, times(1)).saveDepartment(departmentDto);
    }
}