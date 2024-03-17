package org.example.service;

import org.example.dto.AppealDto;

import java.util.List;

public interface AppealService {
    AppealDto saveAppeal(AppealDto appealDto);

    AppealDto getAppeal(Long id);

    AppealDto archiveAppeal(Long id);

    AppealDto registerAppeal(Long id);

    List<AppealDto> getAllAppeals();
}
