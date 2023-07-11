package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Сервис для работы с сущностью Resolution.
 */
@Service
@RequiredArgsConstructor
public class ResolutionService {

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;

    /**
     * Сохраняет резолюцию в базе данных.
     *
     * @param resolutionDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    public ResolutionDto saveResolution(ResolutionDto resolutionDto) {
        Resolution resolution = resolutionMapper.dtoToEntity(resolutionDto);
        resolution.setCreationDate(ZonedDateTime.now());
        resolution.setLastActionDate(ZonedDateTime.now());
        Resolution savedResolution = resolutionRepository.save(resolution);
        return resolutionMapper.entityToDto(savedResolution);
    }

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект DTO резолюции
     */
    public ResolutionDto archiveResolution(Long id) {
        Optional<Resolution> resolutionOptional = resolutionRepository.findById(id);
        if (resolutionOptional.isPresent()) {
            Resolution resolution = resolutionOptional.get();
            resolution.setArchivedDate(ZonedDateTime.now());
            Resolution savedResolution = resolutionRepository.save(resolution);
            return resolutionMapper.entityToDto(savedResolution);
        } else {
            throw new EntityNotFoundException("Resolution with id " + id + " not found");
        }
    }

    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект DTO резолюции
     */
    public ResolutionDto getResolution(Long id) {
        Optional<Resolution> resolutionOptional = resolutionRepository.findById(id);
        if (resolutionOptional.isPresent()) {
            Resolution resolution = resolutionOptional.get();
            return resolutionMapper.entityToDto(resolution);
        } else {
            throw new EntityNotFoundException("Resolution with id " + id + " not found");
        }
    }

    /**
     * Обновляет данные резолюции.
     *
     * @param id идентификатор резолюции
     * @param resolutionDto объект DTO с новыми данными резолюции
     * @return обновленный объект DTO резолюции
     */
    public ResolutionDto updateResolution(Long id, ResolutionDto resolutionDto) {
        Optional<Resolution> resolutionOptional = resolutionRepository.findById(id);
        if (resolutionOptional.isPresent()) {
            Resolution resolution = resolutionOptional.get();
            resolutionMapper.updateEntity(resolutionDto, resolution);
            resolution.setLastActionDate(ZonedDateTime.now());
            Resolution savedResolution = resolutionRepository.save(resolution);
            return resolutionMapper.entityToDto(savedResolution);
        } else {
            throw new EntityNotFoundException("Resolution with id " + id + " not found");
        }
    }
}
