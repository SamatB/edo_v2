package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.ResolutionDto;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.example.service.ResolutionService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Сервис для работы с сущностью Resolution.
 */
@Service
@RequiredArgsConstructor
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
    public ResolutionDto archiveResolution(Long id) {
        return resolutionRepository.findById(id)
                .map(resolution -> {
                    resolution.setArchivedDate(ZonedDateTime.now());
                    return resolution;
                })
                .map(resolutionRepository::save)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Resolution with id " + id + " not found"));
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
}
