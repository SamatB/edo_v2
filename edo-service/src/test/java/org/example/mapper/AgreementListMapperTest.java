package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.*;
import org.example.enums.ApprovalBlockParticipantType;
import org.example.utils.MatchingBlockType;
import org.example.utils.ParticipantType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирования маппера для сущности AgreementList
 */
@Slf4j
@SpringBootTest
class AgreementListMapperTest {
    @Autowired
    private AgreementListMapper agreementListMapper;

    private AgreementList getAgreementList() {
        Appeal appeal = new Appeal();
        appeal.setNumber("AppealNumber");

        Participant initiator = new Participant();
        initiator.setType(ParticipantType.INITIATOR);
        initiator.setNumber(1L);
        initiator.setEmployee(new Employee());

        Participant coordinating = new Participant();
        coordinating.setType(ParticipantType.PARTICIPANT);
        coordinating.setNumber(1L);
        coordinating.setEmployee(new Employee());

        Participant signatory = new Participant();
        signatory.setType(ParticipantType.SIGNER);
        signatory.setNumber(1L);
        signatory.setEmployee(new Employee());

        Set<Participant> coordinatingParticipants = new HashSet<>();
        coordinatingParticipants.add(coordinating);

        Set<Participant> signatoryParticipants = new HashSet<>();
        signatoryParticipants.add(signatory);

        Set<MatchingBlock> coordinatingBlocks = new HashSet<>();
        Set<MatchingBlock> signatoryBlocks = new HashSet<>();

        AgreementList agreementList = new AgreementList();
        agreementList.setComment("AgreementListComment");
        agreementList.setAppeal(appeal);
        agreementList.setInitiator(initiator);
        agreementList.setCoordinating(coordinatingBlocks);
        agreementList.setSignatory(signatoryBlocks);

        MatchingBlock coordinatingBlock = new MatchingBlock();
        coordinatingBlock.setAgreementList(agreementList);
        coordinatingBlock.setApprovalBlockType(ApprovalBlockParticipantType.PARTICIPANT_BLOCK);
        coordinatingBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        coordinatingBlock.setNumber(1L);
        coordinatingBlock.setParticipants(coordinatingParticipants);
        coordinatingBlocks.add(coordinatingBlock);

        MatchingBlock signatoryBlock = new MatchingBlock();
        signatoryBlock.setAgreementList(agreementList);
        signatoryBlock.setApprovalBlockType(ApprovalBlockParticipantType.SIGNER_BLOCK);
        signatoryBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        signatoryBlock.setNumber(1L);
        coordinatingBlock.setParticipants(signatoryParticipants);
        signatoryBlocks.add(signatoryBlock);

        return agreementList;
    }

    private AgreementListDto getAgreementListDto() {
        AppealDto appeal = new AppealDto();
        appeal.setNumber("AppealNumber");

        ParticipantDto initiator = new ParticipantDto();
        initiator.setType("INITIATOR");
        initiator.setNumber(1L);
        initiator.setEmployeeDto(new EmployeeDto());

        ParticipantDto coordinating = new ParticipantDto();
        coordinating.setType("PARTICIPANT");
        coordinating.setNumber(1L);
        coordinating.setEmployeeDto(new EmployeeDto());

        ParticipantDto signatory = new ParticipantDto();
        signatory.setType("SIGNER");
        signatory.setNumber(1L);
        signatory.setEmployeeDto(new EmployeeDto());

        Set<ParticipantDto> coordinatingParticipants = new HashSet<>();
        coordinatingParticipants.add(coordinating);

        Set<ParticipantDto> signatoryParticipants = new HashSet<>();
        signatoryParticipants.add(signatory);

        Set<MatchingBlockDto> coordinatingBlocks = new HashSet<>();
        Set<MatchingBlockDto> signatoryBlocks = new HashSet<>();
        AgreementListDto agreementList = new AgreementListDto();
        agreementList.setComment("AgreementListComment");
        agreementList.setAppealDto(appeal);
        agreementList.setInitiator(initiator);
        agreementList.setCoordinating(coordinatingBlocks);
        agreementList.setSignatory(signatoryBlocks);

        MatchingBlockDto coordinatingBlock = new MatchingBlockDto();
        coordinatingBlock.setAgreementList(agreementList);
        coordinatingBlock.setApprovalBlockType(ApprovalBlockParticipantType.PARTICIPANT_BLOCK);
        coordinatingBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        coordinatingBlock.setNumber(1L);
        coordinatingBlock.setParticipants(coordinatingParticipants);
        coordinatingBlocks.add(coordinatingBlock);

        MatchingBlockDto signatoryBlock = new MatchingBlockDto();
        signatoryBlock.setAgreementList(agreementList);
        signatoryBlock.setApprovalBlockType(ApprovalBlockParticipantType.SIGNER_BLOCK);
        signatoryBlock.setMatchingBlockType(MatchingBlockType.PARALLEL);
        signatoryBlock.setNumber(1L);
        signatoryBlock.setParticipants(signatoryParticipants);
        signatoryBlocks.add(signatoryBlock);

        return agreementList;
    }

    @Test
    @DisplayName("Should return AgreementListDto with correctly filled fields including external entities")
    public void agreementListToAgreementListDto_whenMaps_thenCorrect() {
        AgreementList agreementList = getAgreementList();

        AgreementListDto agreementListDto = agreementListMapper.entityToDto(agreementList);

        assertNotNull(agreementListDto);
        assertNotNull(agreementListDto.getInitiator());
        assertNotNull(agreementListDto.getCoordinating());
        assertNotNull(agreementListDto.getSignatory());
        assertNotNull(agreementListDto.getAppealDto());

        log.info("AgreementListDto successfully created: " + agreementListDto);

        assertEquals(agreementListDto.getComment(), agreementList.getComment());
        assertEquals(agreementListDto.getInitiator().getEmployeeDto().getFioNominative(), agreementList.getInitiator().getEmployee().getFioNominative());
        assertEquals(agreementListDto.getCoordinating().size(), agreementList.getCoordinating().size());
        assertEquals(agreementListDto.getSignatory().size(), agreementList.getSignatory().size());
        assertEquals(agreementListDto.getAppealDto().getNumber(), agreementList.getAppeal().getNumber());
    }

    @Test
    @DisplayName("Should return AgreementList with correctly filled fields including external entities")
    public void participantDtoToParticipant_whenMaps_thenCorrect() {
        AgreementListDto agreementListDto = getAgreementListDto();

        AgreementList agreementList = agreementListMapper.dtoToEntity(agreementListDto);

        assertNotNull(agreementList);
        assertNotNull(agreementList.getInitiator());
        assertNotNull(agreementList.getCoordinating());
        assertNotNull(agreementList.getSignatory());
        assertNotNull(agreementList.getAppeal());

        log.info("AgreementList successfully created: " + agreementList);

        assertEquals(agreementListDto.getComment(), agreementList.getComment());
        assertEquals(agreementListDto.getInitiator().getEmployeeDto().getFioNominative(), agreementList.getInitiator().getEmployee().getFioNominative());
        assertEquals(agreementListDto.getCoordinating().size(), agreementList.getCoordinating().size());
        assertEquals(agreementListDto.getSignatory().size(), agreementList.getSignatory().size());
        assertEquals(agreementListDto.getAppealDto().getNumber(), agreementList.getAppeal().getNumber());
    }
}