package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.DeadlineDto;
import org.example.entity.Deadline;
import org.example.entity.Resolution;
import org.example.mapper.DeadlineMapper;
import org.example.repository.DeadlineRepository;
import org.example.service.DeadlineService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeadlineServiceImpl implements DeadlineService {
    private final DeadlineRepository deadlineRepository;
    private final DeadlineMapper deadlineMapper;

    /**
     * Устанавливает или изменяет дату дедлайна, может быть добавлено описание причины переноса.
     *
     * @param resolutionId идентификатор резолюции, для которой выставляется дедлайн,
     * @param deadlineDto  - ДТО объект дедлайна,
     * @return сохраненный Dto объект дедлайна
     */
    @Override
    @Transactional
    public DeadlineDto setOrUpdateDeadline(Long resolutionId, DeadlineDto deadlineDto) {
        try {
            Deadline deadline = deadlineMapper.dtoToEntity(deadlineDto);
            if (deadlineRepository.findByResolutionId(resolutionId) != null) {
                deadlineRepository.setDeadlineDate(resolutionId, deadline.getDeadlineDate(), deadline.getDescriptionOfDeadlineMoving());
            } else {
                Resolution resolution = new Resolution();
                resolution.setId(resolutionId);
                deadline.setResolution(resolution);
                deadlineRepository.saveAndFlush(deadline);
            }
            Deadline savedDeadline = deadlineRepository.findByResolutionId(resolutionId);
            return Optional.of(savedDeadline)
                    .map(deadlineMapper::entityToDto)
                    .orElseThrow(() -> new IllegalArgumentException("Ошибка обновления дедлайна, проверьте корректность данных"));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при изменении даты дедлайна в БД");
        }
    }
}