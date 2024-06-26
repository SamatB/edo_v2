package org.example.service;

import org.example.dto.AppealDto;

import java.util.List;

public interface AppealService {
    AppealDto saveAppeal(AppealDto appealDto);

    AppealDto getAppeal(Long id);

    AppealDto archiveAppeal(Long id);

    AppealDto registerAppeal(Long id);

    AppealDto reserveNumberForAppeal(AppealDto appealDto);

    List<AppealDto> getPaginatedAppeals(int offset, int size);
}
