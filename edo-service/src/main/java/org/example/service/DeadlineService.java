package org.example.service;

import org.example.dto.DeadlineDto;

import java.util.Collection;

public interface DeadlineService {

    DeadlineDto setOrUpdateDeadline(Long resolutionId, DeadlineDto deadlineDto);

    Collection<DeadlineDto> getDeadlinesByAppeal(Long appealId, Boolean archived);
}
