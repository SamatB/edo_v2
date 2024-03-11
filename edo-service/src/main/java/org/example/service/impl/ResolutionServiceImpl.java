package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.example.service.QuestionService;
import org.example.service.ResolutionService;
import org.example.enums.ResolutionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сущностью Resolution.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResolutionServiceImpl implements ResolutionService {

    private static final String NULL_ERROR = "значение должно быть не null.";
    private static final String VALUE_TYPE_ERROR = "должен быть значением ResolutionType.";
    private static final String VALUE_POSITIVE_ERROR = "значение должно быть положительным.";

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;

    private final QuestionService questionService;

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @Override
    public ResolutionDto saveResolution(ResolutionDto resolutionDto) {
        return Optional.ofNullable(resolutionDto)
                .map(resolutionMapper::dtoToEntity)
                .map(resolution -> {
                    resolution.setCreationDate(ZonedDateTime.now());
                    resolution.setLastActionDate(ZonedDateTime.now());
                    return resolution;
                })
                .map(resolutionRepository::save)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения резолюции: резолюция не должна быть null"));
    }

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект DTO резолюции
     */
    @Override
    @Transactional
    public ResolutionDto archiveResolution(Long id) {

        try {
            resolutionRepository.archiveResolution(id, ZonedDateTime.now());
            return resolutionMapper.entityToDto(resolutionRepository.findById(id).get());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект DTO резолюции
     */
    @Override
    public ResolutionDto getResolution(Long id) {
        return resolutionRepository.findById(id)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Resolution with id " + id + " not found"));
    }

    /**
     * Обновляет данные резолюции.
     *
     * @param id            идентификатор резолюции
     * @param resolutionDto объект DTO с новыми данными резолюции
     * @return обновленный объект DTO резолюции
     */
    @Override
    public ResolutionDto updateResolution(Long id, ResolutionDto resolutionDto) {
        return resolutionRepository.findById(id)
                .map(resolution -> {
                    resolutionMapper.updateEntity(resolutionDto, resolution);
                    resolution.setLastActionDate(ZonedDateTime.now());
                    return resolution;
                })
                .map(resolutionRepository::save)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Resolution with id " + id + " not found"));
    }

    /**
     * Возвращает список объектов Resolution по идентификатору обращения
     *
     * @param appealId - идентификатор обращения
     * @return
     */
    @Override
    public List<Resolution> findAllByAppealIdAndArchivedType(Long appealId, Boolean archived) {
        if (archived == null) {
            return resolutionRepository.findByQuestionIn(questionService.getQuestions(appealId));
        }

        return resolutionRepository.findByQuestionInAndArchivedDateIsTrue(
                questionService.getQuestions(appealId),
                archived
        );

    }

    /**
     * Получает List резолюций в зависимости от их типа архивации.
     *
     * @return List резолюций.
     */

    @Override
    @Transactional(readOnly = true)
    public List<ResolutionDto> findResolution(Boolean archivedType) {
        return resolutionRepository.findResolutions(archivedType);
    }

    /**
     * Проверяет корректность полей резолюции
     * Проверяемые поля: type, serialNumber, signerId
     *
     * @param resolutionDto резолюция
     *                      При выявлении некорректных полей метод бросает исключение
     *                      содержащее некорректные поля с описанием ошибок.
     */
    @Override
    public void validateResolution(ResolutionDto resolutionDto) {
        log.info("Валидация резолюции.");

        StringBuilder message = new StringBuilder();

        String type = resolutionDto.getType();
        final String typeError = String.format("Тип резолюции \"%s\" некорректный - ", type);

        if (type == null) {
            log.error(typeError + NULL_ERROR);
            message.append(typeError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else {
            try {
                ResolutionType.valueOf(type);
            } catch (IllegalArgumentException e) {
                log.error(typeError + VALUE_TYPE_ERROR);
                message.append(typeError)
                        .append(VALUE_TYPE_ERROR)
                        .append(System.lineSeparator());
            }
        }

        Long signerId = resolutionDto.getSignerId();
        final String signerIdError = String.format("Идентификатор подписанта \"%d\" некорректный - ", signerId);
        if (signerId == null) {
            log.error(signerIdError + NULL_ERROR);
            message.append(signerIdError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else if (signerId <= 0) {
            log.error(signerIdError + VALUE_POSITIVE_ERROR);
            message.append(signerIdError)
                    .append(VALUE_POSITIVE_ERROR)
                    .append(System.lineSeparator());
        }

        Integer serialNumber = resolutionDto.getSerialNumber();
        final String serialNumberError = String.format("Серийный номер \"%d\" некорректный - ", serialNumber);
        if (serialNumber == null) {
            log.error(serialNumberError + NULL_ERROR);
            message.append(serialNumberError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else if (serialNumber <= 0) {
            log.error(serialNumberError + VALUE_POSITIVE_ERROR);
            message.append(serialNumberError)
                    .append(VALUE_POSITIVE_ERROR)
                    .append(System.lineSeparator());
        }

        if (!message.isEmpty()) {
            throw new IllegalArgumentException(message.toString());
        }
        log.info("Ошибки не выявлены.");
    }
}
