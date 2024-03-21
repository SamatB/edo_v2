package org.example.service.impl;

import static org.example.utils.EmailHelper.getAgreementListEmailDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.AgreementList;
import org.example.entity.MatchingBlock;
import org.example.entity.Participant;
import org.example.mapper.AgreementListMapper;
import org.example.publisher.AgreementListPublisher;
import org.example.repository.AgreementListRepository;
import org.example.service.AgreementListService;
import org.example.utils.MatchingBlockType;
import org.example.utils.ParticipantStatusType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementListServiceImpl implements AgreementListService {
    private final AgreementListRepository agreementListRepository;
    private final AgreementListMapper agreementListMapper;
    private final AgreementListPublisher agreementListPublisher;

    /**
     * Отправляет лист согласования всем заинтересованным лицам.
     *
     * @param id идентификатор листа согласования
     * @return объект DTO листа согласования
     */
    @Transactional(rollbackFor = Exception.class)
    public AgreementList sendAgreementList(Long id) {
        // TODO: починить маппер для листа согласования и изменить возвращаемый тип на AgreementListDto
        log.info("Отправка листа согласования с идентификатором: {}", id);

        AgreementList result = agreementListRepository.findById(id)
                .map(agreementList -> {
                    String appealNumber = agreementList.getAppeal().getNumber();

                    Set<MatchingBlock> matchingBlocks = agreementList.getSignatory();
                    matchingBlocks.addAll(agreementList.getCoordinating());

                    matchingBlocks
                            .stream()
                            .sorted(Comparator
                                    .comparing(MatchingBlock::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                            .forEachOrdered(mb -> {
                                if (mb.getMatchingBlockType().equals(MatchingBlockType.PARALLEL)) {
                                    mb.getParticipants().forEach(participant -> {
                                        participant.setStatus(ParticipantStatusType.ACTIVE);
                                        agreementListPublisher.sendAgreementListEmailNotification(getAgreementListEmailDto(participant, appealNumber), participant.getNumber());
                                    });
                                } else {
                                    mb.getParticipants()
                                            .stream()
                                            .sorted(Comparator
                                                    .comparing(Participant::getNumber, Comparator.nullsLast(Comparator.naturalOrder())))
                                            .collect(Collectors.toCollection(LinkedHashSet::new))
                                            .stream()
                                            .findFirst()
                                            .ifPresent(participant -> {
                                                participant.setStatus(ParticipantStatusType.ACTIVE);
                                                agreementListPublisher.sendAgreementListEmailNotification(getAgreementListEmailDto(participant, appealNumber), participant.getNumber());
                                            });
                                }
                            });

                    agreementList.setSentApprovalDate(ZonedDateTime.now());
                    log.info("Лист согласования с идентификатором {} отправлен всем заинтересованным лицам в: {}", id, agreementList.getSentApprovalDate());
                    return agreementList;
                })
                .map(agreementListRepository::save)
//                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + id + " не найден"));

//        agreementListMapper.entityToDto(result);
        return result;
    }
}
