package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResolutionDto;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.example.service.ResolutionService;
import org.example.utils.ResolutionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для работы с сущностью Resolution.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResolutionServiceImpl implements ResolutionService {

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;

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
     * @return мапа, содержащая, имена и описания некорректно заполненных полей resolutionDto;
     *         если все проверяемые поля корректные, то возвращается пустая мапа
     */
    @Override
    public Map<String, String> validateResolution(ResolutionDto resolutionDto) {
        log.info("Валидация резолюции");

        Map<String, String> invalidFields = new HashMap<>();

        try {
            ResolutionType.valueOf(resolutionDto.getType());
        } catch (NullPointerException e) {
            final String TYPE_NULL_ERROR = "Тип резолюции не должен быть NULL";
            log.error(TYPE_NULL_ERROR);
            invalidFields.put("type", TYPE_NULL_ERROR);
        } catch (IllegalArgumentException e) {
            final String INVALID_TYPE = "Некорректный тип резолюции";
            log.error(INVALID_TYPE);
            invalidFields.put("type", INVALID_TYPE);
        }

        try {
            if (resolutionDto.getSignerId() <= 0) {
                final String INVALID_SIGNER_ID = "id подписанта должно быть положительным числом";
                log.error(INVALID_SIGNER_ID);
                invalidFields.put("signerId", INVALID_SIGNER_ID);
            }
        } catch (NullPointerException e) {
            final String SIGNER_ID_NULL_ERROR = "id подписанта не должен быть NULL";
            log.error(SIGNER_ID_NULL_ERROR);
            invalidFields.put("signerId", SIGNER_ID_NULL_ERROR);
        }

        try {
            if (resolutionDto.getSerialNumber() <= 0) {
                final String INVALID_SERIAL_NUMBER = "Серийный номер должен быть положительным числом";
                log.error(INVALID_SERIAL_NUMBER);
                invalidFields.put("serialNumber", INVALID_SERIAL_NUMBER);
            }
        } catch (NullPointerException e) {
            final String SERIAL_NUMBER_NULL_ERROR = "Серийный номер не должен быть NULL";
            log.error(SERIAL_NUMBER_NULL_ERROR);
            invalidFields.put("serialNumber", SERIAL_NUMBER_NULL_ERROR);
        }
        return invalidFields;
    }
}
