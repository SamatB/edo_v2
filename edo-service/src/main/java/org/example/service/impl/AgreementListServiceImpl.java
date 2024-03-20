package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.AgreementList;
import org.example.entity.MatchingBlock;
import org.example.entity.Participant;
import org.example.mapper.AgreementListMapper;
import org.example.publisher.AgreementListPublisher;
import org.example.repository.AgreementListRepository;
import org.example.service.AgreementListService;
import org.example.utils.EmailHelper;
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
    private final EmailHelper emailHelper;
    private final AgreementListPublisher agreementListPublisher;

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
     * @param id               идентификатор листа согласования
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
                                        agreementListPublisher.sendAgreementListEmailNotification(emailHelper.getAgreementListEmailDto(participant, appealNumber), participant.getNumber());
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
                                                agreementListPublisher.sendAgreementListEmailNotification(emailHelper.getAgreementListEmailDto(participant, appealNumber), participant.getNumber());
                                            });
                                }
                            });

                    agreementList.setSentApprovalDate(ZonedDateTime.now());
                    return agreementList;
                })
                .map(agreementListRepository::save)
//                .map(agreementListMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Лист согласования с id: " + id + " не найден"));

//        agreementListMapper.entityToDto(result);
        return result;
    }
}
