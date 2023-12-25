package org.example.service;

import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;

import java.time.ZonedDateTime;
import java.util.List;

public interface ResolutionService {
    ResolutionDto saveResolution(ResolutionDto resolutionDto);

    ResolutionDto archiveResolution(Long id);

    ResolutionDto getResolution(Long id);

    ResolutionDto updateResolution(Long id, ResolutionDto resolutionDto);

    List<ResolutionDto> findResolution(Boolean archivedType);

}
