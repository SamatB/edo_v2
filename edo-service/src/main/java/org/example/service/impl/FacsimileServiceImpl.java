package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.FacsimileDto;
import org.example.entity.Facsimile;
import org.example.mapper.FacsimileMapper;
import org.example.repository.FacsimileRepository;
import org.example.service.FacsimileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сущностью Facsimile.
 */
@Service
@RequiredArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final FacsimileRepository facsimileRepository;
    private final FacsimileMapper facsimileMapper;

    @Override
    /**
     * Сохраняет факсимиле в базе данных.
     *
     * @param facsimileDto объект DTO факсимиле
     * @return сохраненный объект DTO факсимиле
     */
    public FacsimileDto saveFacsimile(FacsimileDto facsimileDto) {
        return Optional.ofNullable(facsimileDto)
                .map(facsimileMapper::dtoToEntity)
                .map(facsimileRepository::save)
                .map(facsimileMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения факсимиле, проверьте корректность данных"));
    }

    /**
     * Удаляет факсимиле из базы данных.
     *
     * @param id идентификатор факсимиле, который нужно удалить
     */
    @Override
    @Transactional
    public void deleteFacsimile(Long id) {
        facsimileRepository.deleteById(id);
    }

    /**
     * Изменяет статус архивации факсимиле.
     * Если факсимиле с таким id отсутствует в базе данных - выбрасывается исключение
     * При ошибке изменения статуса выбрасывается исключение
     *
     * @param id идентификатор факсимиле, который следует удалить.
     * @return ответ с Dto объектом факсимиле с измененным статусом архивации
     */
    @Override
    public FacsimileDto toggleArchivedStatus(Long id) {
        Optional<Facsimile> optionalFacsimile = Optional.ofNullable(id)
                .flatMap(facsimileRepository::findById);

        if (optionalFacsimile.isEmpty()) {
            throw new IllegalArgumentException("Факсимиле с id = " + id + " не найдено");
        }

        return optionalFacsimile
                .map(facsimile -> {
                    facsimile.setArchived(!facsimile.isArchived());
                    return facsimileRepository.save(facsimile);
                })
                .map(facsimileMapper::entityToDto)
                .orElseThrow(() -> new IllegalStateException("Ошибка изменения статуса архивации факсимиле: id не должно быть null"));
    }

    @Override
    public List<FacsimileDto> getFacsimilesByArchivedStatus(boolean isArchived) {
        return new ArrayList<>(Optional.of(isArchived)
                .map(facsimileRepository::findByArchived)
                .map(facsimileMapper::entityListToDtoList)
                .orElse(Collections.emptyList()));
    }

    /**
     * Получает список факсимиле с пэйджингом.
     * Метод осуществляет получение списка факсимиле с применением пэйджинга.
     *
     * @param page     Номер страницы для выгрузки (начиная с 0).
     * @param pageSize Количество элементов на странице.
     * @return Список объектов Dto факсимиле на заданной странице с указанным размером страницы.
     */
    @Override
    public List<FacsimileDto> getPaginatedFacsimiles(int page, int pageSize) {
        return new ArrayList<>(Optional.of(PageRequest.of(page, pageSize))
                .map(facsimileRepository::findAll)
                .map(Page::getContent)
                .map(facsimileMapper::entityListToDtoList)
                .orElseGet(Collections::emptyList));
    }
}
