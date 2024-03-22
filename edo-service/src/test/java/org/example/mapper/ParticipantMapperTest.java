package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.ParticipantDto;
import org.example.entity.Employee;
import org.example.entity.MatchingBlock;
import org.example.entity.Participant;
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
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFioNominative("EmployeeFioNominative");
        employee.setEmail("EmployeeEmail");
        employee.setPhone("EmployeePhone");
        employee.setUsername("EmployeeUsername");

        Participant participant = new Participant();
        participant.setEmployee(employee);
        participant.setId(1L);
        participant.setNumber(1L);
        participant.setStatus(ParticipantStatusType.ACTIVE);
        participant.setType(ParticipantType.SIGNER);

        MatchingBlock matchingBlock = new MatchingBlock();
        matchingBlock.setParticipants(Set.of(participant));
        participant.setMatchingBlock(matchingBlock);

        return participant;
    }

    private ParticipantDto getParticipantDto() {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(1L);
        employee.setFioNominative("EmployeeFioNominative");
        employee.setEmail("EmployeeEmail");
        employee.setPhone("EmployeePhone");
        employee.setUsername("EmployeeUsername");

        ParticipantDto participant = new ParticipantDto();
        participant.setEmployeeDto(employee);
        participant.setNumber(1L);
        participant.setStatus("ACTIVE");
        participant.setType("SIGNER");

        return participant;
    }

    @Test
    public void participantToParticipantDto_whenMaps_thenCorrect() {
        Participant participant = getParticipant();

        ParticipantDto participantDto = participantMapper.entityToDto(participant);

        assertEquals(participant.getNumber(), participantDto.getNumber());
        assertEquals(participant.getStatus().name(), participantDto.getStatus());
        assertEquals(participant.getType().name(), participantDto.getType());

        assertNotNull(participantDto.getEmployeeDto());
        log.info("ParticipantDto successfully created: " + participantDto);
        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployeeDto().getEmail());
        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployeeDto().getFioNominative());
        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployeeDto().getPhone());
        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployeeDto().getUsername());
    }

    @Test
    public void participantDtoToParticipant_whenMaps_thenCorrect() {
        ParticipantDto participantDto = getParticipantDto();

        Participant participant = participantMapper.dtoToEntity(participantDto);

        assertEquals(participant.getNumber(), participantDto.getNumber());
        assertEquals(participant.getStatus().name(), participantDto.getStatus());
        assertEquals(participant.getType().name(), participantDto.getType());

        assertNotNull(participant.getEmployee());

        log.info("Participant successfully created: " + participant);

        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployeeDto().getEmail());
        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployeeDto().getFioNominative());
        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployeeDto().getPhone());
        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployeeDto().getUsername());
    }
}