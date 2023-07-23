package org.example.listener;

import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Тесты для класса DepartmentListener.
 */
class DepartmentListenerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentListener departmentListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public final String JSON_DEPARTMENT = "{\n" +
            "  \"id\": 1,\n" +
            "  \"fullName\": \"Отдел разработки\",\n" +
            "  \"shortName\": \"Разраб.\",\n" +
            "  \"address\": \"ул. Примерная, 123\",\n" +
            "  \"addressDetails\": {\n" +
            "    \"fullAddress\": \"ул. Примерная, 123, корпус 1, строение 2, кв. 45\",\n" +
            "    \"street\": \"ул. Примерная\",\n" +
            "    \"house\": \"123\",\n" +
            "    \"index\": \"123456\",\n" +
            "    \"housing\": \"корпус 1\",\n" +
            "    \"building\": \"строение 2\",\n" +
            "    \"city\": \"Город\",\n" +
            "    \"region\": \"Область\",\n" +
            "    \"country\": \"Страна\",\n" +
            "    \"flat\": \"45\"\n" +
            "  },\n" +
            "  \"phone\": \"+12345\",\n" +
            "  \"externalId\": \"EXT-001\",\n" +
            "  \"department\": {\n" +
            "    \"id\": 2,\n" +
            "    \"fullName\": \"Отдел тестирования\",\n" +
            "    \"shortName\": \"Тест.\",\n" +
            "    \"address\": \"ул. Тестовая, 456\",\n" +
            "    \"addressDetails\": {\n" +
            "      \"fullAddress\": \"ул. Примерная, 130, корпус 1, строение 2, кв. 45\",\n" +
            "      \"street\": \"ул. Примерная\",\n" +
            "      \"house\": \"130\",\n" +
            "      \"index\": \"123456\",\n" +
            "      \"housing\": \"корпус 1\",\n" +
            "      \"building\": \"строение 2\",\n" +
            "      \"city\": \"Город\",\n" +
            "      \"region\": \"Область\",\n" +
            "      \"country\": \"Страна\",\n" +
            "      \"flat\": \"45\"\n" +
            "    },\n" +
            "    \"phone\": \"+98765\",\n" +
            "    \"externalId\": \"EXT-002\",\n" +
            "    \"creationDate\": \"2023-07-22T12:34:56+00:00\",\n" +
            "    \"archivedDate\": null\n" +
            "  },\n" +
            "  \"creationDate\": \"2023-07-22T12:34:56+00:00\",\n" +
            "  \"archivedDate\": null\n" +
            "}\n";

    /**
     * Тест для метода receiveDepartment.
     * Проверка корректности преобразования JSON в объект DepartmentDto
     */
    @Test
    void receiveDepartment_ValidJson_ShouldCallSaveDepartment() {
        // Создаем тестовый объект DepartmentDto
        DepartmentDto departmentDto = new DepartmentDto();

        // Задаем значения полям DepartmentDto
        departmentDto.setId(1L);
        departmentDto.setFullName("Отдел разработки");
        departmentDto.setShortName("Разраб.");
        departmentDto.setAddress("ул. Примерная, 123");

        // Создаем объект AddressDetails и задаем его значения
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

        // Задаем объект Address в поле address объекта DepartmentDto
        departmentDto.setAddressDetails(addressDetails);

        departmentDto.setPhone("+12345");
        departmentDto.setExternalId("EXT-001");

        // Создаем вложенный объект DepartmentDto и задаем его значения
        DepartmentDto nestedDepartmentDto = new DepartmentDto();
        nestedDepartmentDto.setId(2L);
        nestedDepartmentDto.setFullName("Отдел тестирования");
        nestedDepartmentDto.setShortName("Тест.");
        nestedDepartmentDto.setAddress("ул. Тестовая, 456");

        // Создаем вложенный объект AddressDetails и задаем его значения
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

        // Задаем объект AddressDetails в поле addressDetails объекта DepartmentDto
        nestedDepartmentDto.setAddressDetails(nestedAddressDetails);

        nestedDepartmentDto.setPhone("+98765");
        nestedDepartmentDto.setExternalId("EXT-002");
        nestedDepartmentDto.setCreationDate(ZonedDateTime.parse("2023-07-22T12:34:56+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        nestedDepartmentDto.setArchivedDate(null);

        // Задаем вложенный объект DepartmentDto в поле department объекта DepartmentDto
        departmentDto.setDepartment(nestedDepartmentDto);

        departmentDto.setCreationDate(ZonedDateTime.parse("2023-07-22T12:34:56+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        departmentDto.setArchivedDate(null);

        when(departmentService.saveDepartment(departmentDto)).thenReturn(departmentDto);

        // Act
        departmentListener.receiveDepartment(Map.of("department", JSON_DEPARTMENT));

        // Assert
        verify(departmentService, times(1)).saveDepartment(departmentDto);
    }
}