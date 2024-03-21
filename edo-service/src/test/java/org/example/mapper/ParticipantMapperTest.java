package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.ParticipantDto;
import org.example.entity.Employee;
import org.example.entity.Participant;
import org.example.utils.ParticipantStatusType;
import org.example.utils.ParticipantType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        participant.setMatchingBlock(null);
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
log.info("participantDto: " + participantDto);
//        assertNotNull(participantDto.getEmployeeDto());
//
//        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployeeDto().getEmail());
//        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployeeDto().getFioNominative());
//        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployeeDto().getPhone());
//        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployeeDto().getUsername());

//        assertEquals(participant.getMatchingBlock(), participantDto.getMatchingBlock());

    }

    @Test
    public void participantDtoToParticipant_whenMaps_thenCorrect() {
        ParticipantDto participantDto = getParticipantDto();

        Participant participant = participantMapper.dtoToEntity(participantDto);

        assertEquals(participant.getNumber(), participantDto.getNumber());
        assertEquals(participant.getStatus().name(), participantDto.getStatus());
        assertEquals(participant.getType().name(), participantDto.getType());

        log.info("participant.getEmployee(): " + participant.getEmployee());
        assertNotNull(participant.getEmployee());


//        assertEquals(participant.getEmployee().getEmail(), participantDto.getEmployeeDto().getEmail());
//        assertEquals(participant.getEmployee().getFioNominative(), participantDto.getEmployeeDto().getFioNominative());
//        assertEquals(participant.getEmployee().getPhone(), participantDto.getEmployeeDto().getPhone());
//        assertEquals(participant.getEmployee().getUsername(), participantDto.getEmployeeDto().getUsername());
    }
}