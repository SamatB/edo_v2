/**
 * Сервис для работы с сущностью Appeal.
 */

package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppealService {

    private final AppealRepository appealRepository;
    private final AppealMapper appealMapper;

    /**
     * Метод для сохранения обращения в базе данных.
     * Если обращение равно null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение обращения используя AppealRepository.
     * @param appealDto объект DTO с новыми данными обращения, которые требуется сохранить в базе данных.
     * @return объект DTO обращения.
     */
    public AppealDto saveAppeal(AppealDto appealDto) {
        Appeal appeal = appealMapper.dtoToEntity(appealDto);
        try {
            Appeal savedAppeal = appealRepository.save(appeal);
            return appealMapper.entityToDto(savedAppeal);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка сохранения обращения: обращение не должно быть null");
        }
    }

    /**
     * Метод для поиска обращения по его id.
     * Если обращение по заданному id не найдено, выбрасывает исключение EntityNotFoundException.
     * Метод выполняет поиск обращения по его id используя AppealRepository.
     * @param id идентификатор обращения.
     * @return объект DTO обращения.
     */
    public AppealDto getAppeal(Long id) {
        Optional<Appeal> appealOptional = appealRepository.findById(id);
        if (appealOptional.isPresent()) {
            Appeal appeal = appealOptional.get();
            return  appealMapper.entityToDto(appeal);
        } else {
            throw new EntityNotFoundException("Ошибка получечния обращения: " +
                    "обращение с указанным идентификатором не найдено");
        }
    }

    /**
     * Метод для добавления даты архивации обращения с указанным id.
     * Если обращение по заданному id не найдено, выбрасывает исключение EntityNotFoundException.
     * Метод выполняет поиск обращения по его id используя AppealRepository.
     * @param id идентификатор архивируемого обращения.
     * @return объект DTO обращения.
     */
    public AppealDto archiveAppeal(Long id) {
        Optional<Appeal> appealOptional = appealRepository.findById(id);
        if (appealOptional.isPresent()) {
            Appeal appeal = appealOptional.get();
            appeal.setArchivedDate(ZonedDateTime.now());
            Appeal archivedAppeal = appealRepository.save(appeal);
            return appealMapper.entityToDto(archivedAppeal);
        } else {
            throw new EntityNotFoundException("Ошибка архивации: обращение с id: " + id + "не найдено");
        }
    }
}
