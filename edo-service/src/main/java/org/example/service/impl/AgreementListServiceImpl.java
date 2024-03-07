package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.MatchingBlock;
import org.example.mapper.AgreementListMapper;
import org.example.repository.AgreementListRepository;
import org.example.service.AgreementListService;
import org.example.service.EmployeeService;
import org.example.utils.MatchingBlockType;
//import org.example.utils.ParticipantStatusType;
import org.example.utils.ParticipantType;
//import org.example.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementListServiceImpl implements AgreementListService {
    private final AgreementListRepository agreementListRepository;
    private final AgreementListMapper agreementListMapper;
//    private final EmailSenderProvider emailSenderProvider;
//    private final EmailService emailService;


    /**
     * Сохраняет лист согласования в базе данных.
     *
     * @param agreementListDto объект DTO листа согласования
     * @return сохраненный объект DTO листа согласования
     */
    @Transactional(rollbackFor = Exception.class)
    public AgreementListDto saveAgreementList(AgreementListDto agreementListDto) {
        return Optional.ofNullable(agreementListDto)
                .map(agreementListMapper::dtoToEntity)
                .map(agreementList -> {
                    agreementList.setCreationDate(ZonedDateTime.now());
                    return agreementList;
                })
                .map(agreementListRepository::save)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения листа согласования: лист согласования не должен быть null"));
    }

    /**
     * Возвращает лист согласования по его идентификатору.
     *
     * @param id идентификатор листа согласования
     * @return объект DTO листа согласования
     */
    public AgreementListDto getAgreementList(Long id) {
        return agreementListRepository.findById(id)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + id + " не найден"));
    }

    /**
     * Обновляет лист согласования в базе данных.
     *
     * @param id идентификатор листа согласования
     * @param agreementListDto объект DTO листа согласования
     * @return обновленный объект DTO листа согласования
     */
    @Transactional(rollbackFor = Exception.class)
    public AgreementListDto updateAgreementList(Long id, AgreementListDto agreementListDto) {
        return agreementListRepository.findById(id)
                .map(agreementList -> {
                    agreementListMapper.updateEntity(agreementListDto, agreementList);
                    return agreementList;
                })
                .map(agreementListRepository::save)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + id + " не найден"));
    }

    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     * @param id идентификатор листа согласования
     * @return объект DTO листа согласования
     */
    @Transactional(rollbackFor = Exception.class)
    public AgreementListDto sendAgreementList(Long id) {
        // TODO - [ ] получить из него список заинтересованных лиц
        //- [ ] определить порядок блоков и создать очередь из блоков на основании их номера
        //- [ ] определить тип блоков и для очередных создать еще одну очередь
        //- [ ] для параллельных обрабатываем всех разом -
        //[ ] для этого согласующего меняется статус из пассивного в активный
        //- [ ] сформировать текст письма strinf с ФИО участника и appealNumber
        // - [ ] встроиться в существующую логику в edo-integration для отправки email

//        EmployeeDto employee1 = employeeService.getEmployeeById(1L);
//        EmployeeDto employee2 = employeeService.getEmployeeById(2L);
//        EmployeeDto employee3 = employeeService.getEmployeeById(3L);
//        List<EmployeeDto> employees = Arrays.asList(employee1, employee2, employee3);
//
//        AppealDto appeal = new AppealDto();
//        appeal.setCreator(employee1);
//        appeal.setAddressee(employees);
//        appeal.setSingers(employees);
//
//        ParticipantDto initiator = new ParticipantDto();
//        initiator.setType(String.valueOf(ParticipantType.INITIATOR));
//        initiator.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        initiator.setCreateDate(ZonedDateTime.now());
//        initiator.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        initiator.setAcceptDate(ZonedDateTime.now());
//        initiator.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        initiator.setNumber(1L);
//
//        ParticipantDto random1 = new ParticipantDto();
//        random1.setType(String.valueOf(ParticipantType.SIGNER));
//        random1.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random1.setCreateDate(ZonedDateTime.now());
//        random1.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random1.setAcceptDate(ZonedDateTime.now());
//        random1.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random1.setNumber(1L);
//
//        ParticipantDto random2 = new ParticipantDto();
//        random2.setType(String.valueOf(ParticipantType.SIGNER));
//        random2.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random2.setCreateDate(ZonedDateTime.now());
//        random2.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random2.setAcceptDate(ZonedDateTime.now());
//        random2.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random2.setNumber(1L);
//
//        ParticipantDto random3 = new ParticipantDto();
//        random3.setType(String.valueOf(ParticipantType.SIGNER));
//        random3.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random3.setCreateDate(ZonedDateTime.now());
//        random3.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random3.setAcceptDate(ZonedDateTime.now());
//        random3.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random3.setNumber(1L);
//
//        ParticipantDto random4 = new ParticipantDto();
//        random4.setType(String.valueOf(ParticipantType.SIGNER));
//        random4.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random4.setCreateDate(ZonedDateTime.now());
//        random4.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random4.setAcceptDate(ZonedDateTime.now());
//        random4.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random4.setNumber(1L);
//
//        ParticipantDto random5 = new ParticipantDto();
//        random5.setType(String.valueOf(ParticipantType.SIGNER));
//        random5.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random5.setCreateDate(ZonedDateTime.now());
//        random5.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random5.setAcceptDate(ZonedDateTime.now());
//        random5.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random5.setNumber(1L);
//
//        ParticipantDto random6 = new ParticipantDto();
//        random6.setType(String.valueOf(ParticipantType.SIGNER));
//        random6.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random6.setCreateDate(ZonedDateTime.now());
//        random6.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random6.setAcceptDate(ZonedDateTime.now());
//        random6.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random6.setNumber(1L);
//
//        ParticipantDto random7 = new ParticipantDto();
//        random7.setType(String.valueOf(ParticipantType.SIGNER));
//        random7.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random7.setCreateDate(ZonedDateTime.now());
//        random7.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random7.setAcceptDate(ZonedDateTime.now());
//        random7.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random7.setNumber(1L);
//
//        ParticipantDto random8 = new ParticipantDto();
//        random8.setType(String.valueOf(ParticipantType.SIGNER));
//        random8.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random8.setCreateDate(ZonedDateTime.now());
//        random8.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random8.setAcceptDate(ZonedDateTime.now());
//        random8.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random8.setNumber(1L);
//
//        ParticipantDto random9 = new ParticipantDto();
//        random9.setType(String.valueOf(ParticipantType.SIGNER));
//        random9.setStatus(String.valueOf(ParticipantStatusType.PASSIVE));
//        random9.setCreateDate(ZonedDateTime.now());
//        random9.setUntilDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random9.setAcceptDate(ZonedDateTime.now());
//        random9.setExpiredDate(ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
//        random9.setNumber(1L);
//
//        Set<ParticipantDto> participants = new HashSet<>();
//        participants.add(random1);
//        participants.add(random2);
//        participants.add(random3);
//
//        Set<ParticipantDto> participants2 = new HashSet<>();
//        participants2.add(random4);
//        participants2.add(random5);
//        participants2.add(random6);
//
//        Set<ParticipantDto> participants3 = new HashSet<>();
//        participants3.add(random7);
//        participants3.add(random8);
//        participants3.add(random9);
//
//        MatchingBlockDto matchingBlock1 = new MatchingBlockDto();
//        matchingBlock1.setNumber(1L);
//        matchingBlock1.setMatchingBlockType(MatchingBlockType.ALTERNATE);
//        matchingBlock1.setParticipants(participants);
//
//        MatchingBlockDto matchingBlock2 = new MatchingBlockDto();
//        matchingBlock2.setNumber(2L);
//        matchingBlock2.setMatchingBlockType(MatchingBlockType.PARALLEL);
//        matchingBlock2.setParticipants(participants2);
//
//        MatchingBlockDto matchingBlock3 = new MatchingBlockDto();
//        matchingBlock3.setNumber(3L);
//        matchingBlock3.setMatchingBlockType(MatchingBlockType.ALTERNATE);
//        matchingBlock3.setParticipants(participants3);
//
//        Set<MatchingBlockDto> coordinatingBlocks = new HashSet<>();
//        coordinatingBlocks.add(matchingBlock1);
//
//        Set<MatchingBlockDto> signatoryBlocks = new HashSet<>();
//        signatoryBlocks.add(matchingBlock2);
//        signatoryBlocks.add(matchingBlock3);
//
//        AgreementListDto agreementListDto = new AgreementListDto();
//        agreementListDto.setComment("Лист согласования for testing");
//        agreementListDto.setAppealDto(appeal);
//        agreementListDto.setInitiator(initiator);
//        agreementListDto.setCoordinating(coordinatingBlocks);
//        agreementListDto.setSignatory(signatoryBlocks);
//
//        agreementListDto.getSignatory().stream()
//                .sorted(Comparator.comparing(MatchingBlockDto::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
//                .map(block -> {
//                    if (block.getMatchingBlockType() == MatchingBlockType.PARALLEL) {
//                        block.getParticipants().forEach(participant -> {
//                            String emailText = "Добрый день, " + participant.getEmployeeDto().getFioNominative() +"! На ваше имя пришёл Лист Согласования по обращению номер " + agreementListDto.getAppealDto().getNumber() + ".";
//                            participant
//                                    .setStatus(String.valueOf(ParticipantStatusType.ACTIVE));
////                            emailService.sendEmail(participant.getEmployeeDto().getEmail(), "Уведомление", emailText);
//                        });
//                    }
//                    block
//                            .getParticipants()
//                            .stream()
//                            .sorted(Comparator.comparing(ParticipantDto::getNumber, Comparator.nullsLast(Comparator.naturalOrder())));
//                    return null;
//                });

        return agreementListRepository.findById(id)
                .map(agreementList -> {
                    agreementList
                            .getSignatory()
                            .stream()
                            .sorted(Comparator.comparing(MatchingBlock::getNumber, Comparator.nullsLast(Comparator.naturalOrder())));
                    agreementList.setSentApprovalDate(ZonedDateTime.now());
                    return agreementList;
                })
                .map(agreementListRepository::save)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + id + " не найден"));
//        return null;
    }
}
