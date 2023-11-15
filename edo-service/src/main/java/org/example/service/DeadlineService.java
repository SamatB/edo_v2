package org.example.service;

import org.example.dto.DeadlineDto;

import java.time.ZonedDateTime;

public interface DeadlineService {

    DeadlineDto setOrUpdateDeadline(Long resolutionId, DeadlineDto deadlineDto);
}
