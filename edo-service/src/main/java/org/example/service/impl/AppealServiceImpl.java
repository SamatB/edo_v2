/**
 * Сервис для работы с сущностью Appeal.
 */

package org.example.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.mapper.AppealMapper;
import org.example.repository.AppealRepository;
import org.example.service.AppealService;
import org.example.enums.StatusType;
import org.example.service.NomenclatureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final AppealMapper appealMapper;
    private final NomenclatureService nomenclatureService;

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
                .stream()
                .peek(appeal -> {
                    appeal.setNumber(nomenclatureService.generateNumberForAppeal(appeal.getNomenclature()));
                    appeal.setStatusType(StatusType.NOT_REGISTERED);
                })
                .findFirst()
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
                .orElseThrow(() -> new EntityNotFoundException("Ошибка получения обращения: обращение с указанным id: " + id + " не найдено"));
    }

    /**
     * Метод для добавления даты архивации обращения с указанным id.
     * Если обращение с указанным id не найдено, выбрасывает исключение EntityNotFoundException.
     * Метод выполняет поиск обращения по его id используя AppealRepository.
     *
     * @param id идентификатор архивируемого обращения.
     * @return объект DTO обращения.
     */
    public AppealDto archiveAppeal(Long id) {
        return appealRepository.findById(id)
                .map(appeal -> {
                    appeal.setArchivedDate(ZonedDateTime.now());
                    appeal.setStatusType(StatusType.ARCHIVE);
                    return appeal;
                })
                .map(appealRepository::save)
                .map(appealMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка архивации: обращение с id: " + id + "не найдено"));
    }

    /**
     * Метод для регистрации обращения с указанным id.
     * Если обращение с указанным id не найдено, выбрасывает исключение EntityNotFoundException.
     * Если обращение уже ранее было зарегистрировано, выбрасывает исключение EntityExistsException.
     *
     * @param id идентификатор регистрируемого обращения.
     * @return объект DTO обращения в случае успешной регистрации.
     */
    @Override
    @Transactional
    public AppealDto registerAppeal(Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка регистрации: обращение с id: " + id + " не найдено"));
        if (appeal.getStatusType() == StatusType.REGISTERED) {
            throw new EntityExistsException("Ошибка регистрации: обращение с id: " + id + " ранее зарегистрировано");
        }
        appeal.setRegistrationDate(ZonedDateTime.now());
        appeal.setStatusType(StatusType.REGISTERED);
        appealRepository.save(appeal);
        return appealMapper.entityToDto(appeal);
    }

    /**
     * Метод для резервирования номера обращения по указанному номеру.
     * Если обращение с указанным номеров не найдено, выбрасывает исключение EntityNotFoundException.
     * Если у обращения уже ранее был зарезервирован номер, выбрасывает исключение EntityExistsException.
     *
     * @param appealNumber номер обращения, для которого резервируется номер.
     * @return объект DTO обращения в случае успешной резервации.
     */
    @Override
    @Transactional
    public AppealDto reserveNumberForAppeal(String appealNumber) {
        Appeal appeal = appealRepository.findAllByNumber(appealNumber)
                .stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Ошибка резервирования номера: обращение с номером: " + appealNumber + " не найдено"));
        if (appeal.getReservedNumber() != null) {
            throw new EntityExistsException("Ошибка резервирования номера: у обращения с номером " + appealNumber + " был ранее зарезервирован номер " + appeal.getReservedNumber());
        }
        appeal.setReservedNumber(nomenclatureService.generateNumberForAppeal(appeal.getNomenclature()));
        appealRepository.save(appeal);
        return appealMapper.entityToDto(appeal);
    }
}
