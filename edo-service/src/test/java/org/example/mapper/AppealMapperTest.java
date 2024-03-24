package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.*;
import org.example.enums.StatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирования маппера для сущности Appeal
 */
@Slf4j
@SpringBootTest
class AppealMapperTest {
    @Autowired
    AppealMapper appealMapper;
    private final ZonedDateTime dateTime = ZonedDateTime.now();
    @Test
    @DisplayName("Should correctly map AppealDto to Appeal")
    void appealDtoToAppeal() {
        AppealDto appealDto = getAppealDto();
        Appeal appeal = getAppeal();

        Appeal result = appealMapper.dtoToEntity(appealDto);

        assertNotNull(result.getCreator());
        assertNotNull(result.getSingers());
        assertNotNull(result.getAddressee());
        assertNotNull(result.getNomenclature());
//        assertNotNull(result.getRegion());

        log.info("Appeal successfully created: " + appeal);
        log.info(result.getStatusType().getRusStatusType());

        assertEquals(appeal, result);
        assertEquals(appealDto.getCreator().getAddressDetails().getFullAddress(), result.getCreator().getAddressDetails().getFullAddress());
        assertEquals(appealDto.getNomenclature().getDepartment().getAddressDetails().getFullAddress(), result.getNomenclature().getDepartment().getAddressDetails().getFullAddress());
    }

    @Test
    @DisplayName("Should correctly map Appeal to AppealDto")
    void appealToAppealDto() {
        AppealDto appealDto = getAppealDto();
        Appeal appeal = getAppeal();

        AppealDto result = appealMapper.entityToDto(appeal);

        assertNotNull(result.getCreator());
        assertNotNull(result.getSingers());
        assertNotNull(result.getAddressee());
        assertNotNull(result.getNomenclature());
//        assertNotNull(result.getRegion());

        log.info("AppealDto successfully created: " + appealDto);
        log.info(result.getStatusType().getRusStatusType());

        assertEquals(appealDto.getId(), result.getId());
        assertEquals(appeal.getCreator().getAddressDetails().getFullAddress(), result.getCreator().getAddressDetails().getFullAddress());
        assertEquals(appeal.getNomenclature().getDepartment().getAddressDetails().getFullAddress(), result.getNomenclature().getDepartment().getAddressDetails().getFullAddress());
        assertEquals(appeal.getSingers().size(), result.getSingers().size());
        assertEquals(appeal.getAddressee().size(), result.getAddressee().size());
    }

    private AppealDto getAppealDto() {
        AppealDto appealDto = new AppealDto();
        appealDto.setId(1L);
        appealDto.setCreationDate(dateTime);
        appealDto.setRegistrationDate(dateTime);
        appealDto.setNumber("1");
        appealDto.setReservedNumber("1");
        appealDto.setAnnotation("1");
        appealDto.setStatusType(StatusType.REGISTERED);
        appealDto.setCreator(getEmployeeDto());
        appealDto.setSingers(List.of(getEmployeeDto()));
        appealDto.setAddressee(List.of(getEmployeeDto()));
        appealDto.setNomenclature(getNomenclatureDto());
        return appealDto;
    }

    private Appeal getAppeal() {
        return Appeal.builder()
                .id(1L)
                .creationDate(dateTime)
                .registrationDate(dateTime)
                .number("1")
                .reservedNumber("1")
                .annotation("1")
                .statusType(StatusType.REGISTERED)
                .creator(getEmployee())
                .singers(List.of(getEmployee()))
                .addressee(List.of(getEmployee()))
                .nomenclature(getNomenclature())
                .build();
    }

    private EmployeeDto getEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("1");
        employeeDto.setLastName("1");
        employeeDto.setAddressDetails(getAddressDetailsDto());
        employeeDto.setDepartment(getDepartmentDto());
        return employeeDto;
    }

    private Employee getEmployee() {
        return Employee.builder()
                .id(1L)
                .firstName("1")
                .lastName("1")
                .addressDetails(getAddressDetails())
                .department(getDepartment())
                .build();
    }

    private NomenclatureDto getNomenclatureDto() {
        return NomenclatureDto.builder()
                .department(getDepartmentDto())
                .build();
    }

    private Nomenclature getNomenclature() {
        return Nomenclature.builder()
                .department(getDepartment())
                .build();
    }

    private DepartmentDto getDepartmentDto() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setAddressDetails(getAddressDetailsDto());
        return departmentDto;
    }

    private Department getDepartment() {
        return Department.builder()
                .addressDetails(getAddressDetails())
                .build();
    }

    private AddressDto getAddressDetailsDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setFullAddress("1");
        return addressDto;
    }

    private Address getAddressDetails() {
        return Address.builder()
                .fullAddress("1")
                .build();
    }
}