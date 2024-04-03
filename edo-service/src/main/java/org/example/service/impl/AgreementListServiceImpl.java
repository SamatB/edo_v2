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
                    sendAgreementListEmailNotification(agreementList.getSignatory(), appealNumber);
                    sendAgreementListEmailNotification(agreementList.getCoordinating(), appealNumber);

                    log.info("Лист согласования с идентификатором {} отправлен всем заинтересованным лицам", agreementListId);
                    return agreementList;
                })
                .map(agreementListRepository::save)
                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + agreementListId + " не найден"));
    }

    /**
     * Метод находит блок с наименьшим номером в сете блоков согласования и в зависимости от типа блока отправляет лист согласования либо всем заинтересованным лицам, либо только первому в сете.
     *
     * @param matchingBlockSet сет блоков согласования
     * @param appealNumber     номер обращения
     */
    private void sendAgreementListEmailNotification(Set<MatchingBlock> matchingBlockSet, String appealNumber) {
        matchingBlockSet
                .stream()
                .min(Comparator
                        .comparing(MatchingBlock::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                .ifPresent(mb -> {
                    if (mb.getMatchingBlockType().equals(MatchingBlockType.PARALLEL)) {
                        mb.getParticipants().forEach(participant -> sendAgreementListEmailNotification(participant, appealNumber));
                    } else {
                        mb.getParticipants()
                                .stream()
                                .min(Comparator
                                        .comparing(Participant::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                                .ifPresent(participant -> sendAgreementListEmailNotification(participant, appealNumber));
                    }
                });
    }

    /**
     * Метод устанавливает статус активного участника и отправляет уведомление по электронной почте
     *
     * @param participant  сущность участника
     * @param appealNumber номер обращения
     */
    private void sendAgreementListEmailNotification(Participant participant, String appealNumber) {
        participant.setStatus(ParticipantStatusType.ACTIVE);
        agreementListPublisher.sendAgreementListEmailNotification(getAgreementListEmailDto(participantMapper.entityToDto(participant), appealNumber), participant.getNumber());
    }
}
