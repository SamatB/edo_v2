package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.*;
import org.example.enums.ApprovalBlockParticipantType;
import org.example.utils.MatchingBlockType;
import org.example.utils.ParticipantStatusType;
import org.example.utils.ParticipantType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирования маппера для сущности Participant
 */
@Slf4j
@SpringBootTest
class ParticipantMapperTest {
    @Autowired
    ParticipantMapper participantMapper;

    private Participant getParticipant() {
        Participant participant = new Participant();
        participant.setEmployee(getEmployee());
        participant.setId(1L);
        participant.setNumber(1L);
        participant.setStatus(ParticipantStatusType.ACTIVE);
        participant.setType(ParticipantType.SIGNER);

        MatchingBlock matchingBlock = new MatchingBlock();
        matchingBlock.setParticipants(Set.of(participant));
        matchingBlock.setNumber(1L);
        matchingBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        matchingBlock.setApprovalBlockType(ApprovalBlockParticipantType.SIGNER_BLOCK);
        participant.setMatchingBlock(matchingBlock);

        return participant;
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
        employee.setAddressDetails(address);
        employee.setDepartment(department);
        return employee;
    }

    private ParticipantDto getParticipantDto() {
        ParticipantDto participant = new ParticipantDto();
        participant.setEmployee(getEmployeeDto());
        participant.setNumber(1L);
        participant.setStatus("ACTIVE");
        participant.setType("SIGNER");

        MatchingBlockDto matchingBlock = new MatchingBlockDto();
        matchingBlock.setParticipants(Set.of(participant));
        matchingBlock.setNumber(1L);
        matchingBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        matchingBlock.setApprovalBlockType(ApprovalBlockParticipantType.SIGNER_BLOCK);
        participant.setMatchingBlock(matchingBlock);

        return participant;
    }

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

        EmployeeDto employee = new EmployeeDto();
        employee.setId(1L);
        employee.setFioNominative("EmployeeFioNominative");
        employee.setEmail("EmployeeEmail");
        employee.setPhone("EmployeePhone");
        employee.setUsername("EmployeeUsername");
        employee.setAddressDetails(address);
        employee.setDepartment(departmentDto);
        return employee;
    }

    @Test
    public void participantToParticipantDto_whenMaps_thenCorrect() {
        Participant participant = getParticipant();

        ParticipantDto participantDto = participantMapper.entityToDto(participant);

        assertEquals(participant.getNumber(), participantDto.getNumber());
        assertEquals(participant.getStatus().name(), participantDto.getStatus());
        assertEquals(participant.getType().name(), participantDto.getType());

        assertNotNull(participantDto.getEmployee());
        assertNotNull(participantDto.getEmployee().getAddressDetails());
        assertNotNull(participantDto.getEmployee().getDepartment());
        assertNotNull(participantDto.getMatchingBlock());

        log.info("ParticipantDto successfully created: " + participantDto);
        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployee().getEmail());
        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployee().getFioNominative());
        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployee().getPhone());
        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployee().getUsername());
        assertEquals(participant.getMatchingBlock().getNumber(), participantDto.getMatchingBlock().getNumber());
        assertEquals(participant.getMatchingBlock().getApprovalBlockType(), participantDto.getMatchingBlock().getApprovalBlockType());
        assertEquals(participant.getMatchingBlock().getMatchingBlockType(), participantDto.getMatchingBlock().getMatchingBlockType());
    }

    @Test
    public void participantDtoToParticipant_whenMaps_thenCorrect() {
        ParticipantDto participantDto = getParticipantDto();

        Participant participant = participantMapper.dtoToEntity(participantDto);

        assertEquals(participant.getNumber(), participantDto.getNumber());
        assertEquals(participant.getStatus().name(), participantDto.getStatus());
        assertEquals(participant.getType().name(), participantDto.getType());
        assertNotNull(participant.getMatchingBlock());

        assertNotNull(participant.getEmployee());
        assertNotNull(participant.getEmployee().getAddressDetails());
        assertNotNull(participant.getEmployee().getDepartment());
        assertNotNull(participant.getMatchingBlock());

        log.info("Participant successfully created: " + participant);
        log.info("Participant matching block number: {}", participant.getMatchingBlock().getNumber());

        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployee().getEmail());
        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployee().getFioNominative());
        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployee().getPhone());
        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployee().getUsername());
    }
}