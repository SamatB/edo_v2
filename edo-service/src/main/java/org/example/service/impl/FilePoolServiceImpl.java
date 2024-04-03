package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.FilePoolDto;
import org.example.entity.FilePool;
import org.example.mapper.FilePoolMapper;
import org.example.repository.FilePoolRepository;
import org.example.service.FilePoolService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {
    private final FilePoolRepository filePoolRepository;
    private final FilePoolMapper filePoolMapper;

    /**
     * Метод для сохранения FilePool в базе данных.
     * Если FilePool равен null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение FilePool используя FilePoolRepository.
     *
     * @param filePoolDto объект DTO с новыми данными FilePool, которые требуется сохранить в базе данных.
     * @return объект DTO FilePool.
     */
    @Override
    public FilePoolDto saveFilePool(FilePoolDto filePoolDto) {
        FilePool filePool = filePoolMapper.dtoToEntity(filePoolDto);
        try {
            FilePool savedFilePool = filePoolRepository.save(filePool);
            return filePoolMapper.entityToDto(savedFilePool);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка сохранения обращения: обращение не должно быть null");
        }
    }

    /**
     * Метод для получения списка UUID FilePool, относящихся к обращениям, созданным более 5 лет назад.
     *
     * @return список UUID.
     */
    @Override
    public List<UUID> getUUIDByCreationDateBeforeFiveYears() {
        try {
            return filePoolRepository.findFilePoolByCreationDateBefore(ZonedDateTime.now().minusYears(5)).stream()
                    .map(FilePool::getStorageFileId)
                    .collect(Collectors.toList());
        } catch (DataAccessException | DateTimeException e) {
            throw new DateTimeException("Ошибка при определении даты или доступа к БД");
        }
    }

    /**
     * Метод для изменения признака, что файл удален из FileStorage.
     */
    @Override
    @Transactional
    public void markThatTheFileHasBeenDeletedFromStorage(List<UUID> uuidList) {
        try {
            filePoolRepository.changeRemovedField(uuidList);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при доступе к БД");
        }
    }
}

