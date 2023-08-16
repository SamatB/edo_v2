package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.FilePoolDto;
import org.example.entity.FilePool;
import org.example.mapper.FilePoolMapper;
import org.example.repository.FilePoolRepository;
import org.example.service.FilePoolService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {
    private final FilePoolRepository filePoolRepository;
    private final FilePoolMapper filePoolMapper;

    /**
     * Метод для сохранения FilePool в базе данных.
     * Если FilePool равен null, то выбрасывается исключение IllegalArgumentException.
     * Метод выполняет сохранение FilePool используя FilePoolRepository.
     * @param filePoolDto объект DTO с новыми данными FilePool, которые требуется сохранить в базе данных.
     * @return объект DTO FilePool.
     */
    @Override
    public FilePoolDto saveFilePool(FilePoolDto filePoolDto) {
        FilePool filePool = filePoolMapper.dtoToEntity(filePoolDto);
        try {
            FilePool savedFilePool = filePoolRepository.save(filePool);
            return filePoolMapper.entityToDto(savedFilePool);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка сохранения обращения: обращение не должно быть null");
        }
    }
}
