package org.example.service;

import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface ResolutionService {
    ResolutionDto saveResolution(ResolutionDto resolutionDto);

    ResolutionDto archiveResolution(Long id);

    ResolutionDto getResolution(Long id);

    ResolutionDto updateResolution(Long id, ResolutionDto resolutionDto);

    List<ResolutionDto> findResolution(Boolean archivedType);

    Map<String, String> validateResolution(ResolutionDto resolutionDto);
}
