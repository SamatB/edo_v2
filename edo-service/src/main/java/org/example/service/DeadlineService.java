package org.example.service;

import org.example.dto.DeadlineDto;

import java.util.List;

public interface DeadlineService {

    DeadlineDto setOrUpdateDeadline(Long resolutionId, DeadlineDto deadlineDto);

    List<DeadlineDto> getDeadlinesByAppeal(Long appealId, Integer archived);
}
