package org.example.service;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dto.DeadlineDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZonedDateTime;
import java.util.List;

public interface DeadlineService {

    DeadlineDto setOrUpdateDeadline(Long resolutionId, DeadlineDto deadlineDto);
    List<DeadlineDto> getResolutionDeadlines(Long appealId, Boolean archived);

}
