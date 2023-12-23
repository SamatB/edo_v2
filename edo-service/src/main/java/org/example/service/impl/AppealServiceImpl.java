/**
 * Сервис для работы с сущностью Appeal.
 */

package org.example.service.impl;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.service.AppealService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final AppealMapper appealMapper;
    /**
     * Метод для сохранения обращения в базе данных.
     * Если обращение равно null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение обращения используя AppealRepository.
     *
     * @param appealDto объект DTO с новыми данными обращения, которые требуется сохранить в базе данных.
     * @return объект DTO обращения.
     */
    public AppealDto saveAppeal(AppealDto appealDto) {
        return Optional.ofNullable(appealDto)
                .map(appealMapper::dtoToEntity)
                .map(appealRepository::save)
                .map(appealMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения обращения: обращение не должно быть null"));
    }

    /**
     * Метод для поиска обращения по его id.
     * Если обращение по заданному id не найдено, выбрасывает исключение EntityNotFoundException.
     * Метод выполняет поиск обращения по его id используя AppealRepository.
     *
     * @param id идентификатор обращения.
     * @return объект DTO обращения.
     */
    public AppealDto getAppeal(Long id) {
        return appealRepository.findById(id)
                .map(appealMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка получечния обращения: обращение с указанным id: " + id + " не найдено"));
    }

    /**
     * Метод для добавления даты архивации обращения с указанным id.
     * Если обращение по заданному id не найдено, выбрасывает исключение EntityNotFoundException.
     * Метод выполняет поиск обращения по его id используя AppealRepository.
     *
     * @param id идентификатор архивируемого обращения.
     * @return объект DTO обращения.
     */
    public AppealDto archiveAppeal(Long id) {
        return appealRepository.findById(id)
                .map(appeal -> {
                    appeal.setArchivedDate(ZonedDateTime.now());
                    return appeal;
                })
                .map(appealRepository::save)
                .map(appealMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка архивации: обращение с id: " + id + "не найдено"));
    }
}
