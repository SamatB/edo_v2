package org.example.service.impl;

import static org.example.utils.EmailHelper.getAgreementListEmailDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AgreementListDto;
import org.example.entity.MatchingBlock;
import org.example.entity.Participant;
import org.example.mapper.AgreementListMapper;
import org.example.mapper.ParticipantMapper;
import org.example.publisher.AgreementListPublisher;
import org.example.repository.AgreementListRepository;
import org.example.service.AgreementListService;
import org.example.utils.MatchingBlockType;
import org.example.utils.ParticipantStatusType;

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
    private final ParticipantMapper participantMapper;
    private final AgreementListPublisher agreementListPublisher;

    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     *
     * @param agreementListId идентификатор листа согласования
     * @return объект DTO листа согласования
     */
    @Transactional(rollbackFor = Exception.class)
    public AgreementListDto sendAgreementList(Long agreementListId) {
        log.info("Отправка листа согласования с идентификатором: {}", agreementListId);

        return agreementListRepository.findById(agreementListId)
                .map(agreementList -> {
                    String appealNumber = agreementList.getAppeal().getNumber();
                    Set<MatchingBlock> matchingBlocks = new HashSet<>(agreementList.getSignatory());
                    matchingBlocks.addAll(agreementList.getCoordinating());

                    matchingBlocks
                            .stream()
                            .sorted(Comparator
                                    .comparing(MatchingBlock::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                            .forEachOrdered(mb -> {
                                if (mb.getMatchingBlockType().equals(MatchingBlockType.PARALLEL)) {
                                    mb.getParticipants().forEach(participant -> {
                                        participant.setStatus(ParticipantStatusType.ACTIVE);
                                        agreementListPublisher.sendAgreementListEmailNotification(getAgreementListEmailDto(participantMapper.entityToDto(participant), appealNumber), participant.getNumber());
                                    });
                                } else {
                                    mb.getParticipants()
                                            .stream()
                                            .min(Comparator
                                                    .comparing(Participant::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                                            .ifPresent(participant -> {
                                                participant.setStatus(ParticipantStatusType.ACTIVE);
                                                agreementListPublisher.sendAgreementListEmailNotification(getAgreementListEmailDto(participantMapper.entityToDto(participant), appealNumber), participant.getNumber());
                                            });
                                }
                            });

                    agreementList.setSentApprovalDate(ZonedDateTime.now());
                    log.info("Лист согласования с идентификатором {} отправлен всем заинтересованным лицам в: {}", agreementListId, agreementList.getSentApprovalDate());
                    return agreementList;
                })
                .map(agreementListRepository::save)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + agreementListId + " не найден"));
    }
}
