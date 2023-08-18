package org.example.service;

import org.example.dto.FacsimileDto;

import java.util.List;

public interface FacsimileService {

    FacsimileDto saveFacsimile(FacsimileDto facsimile);

    void deleteFacsimile(Long id);

    void toggleArchivedStatus(Long id);

    List<FacsimileDto> getFacsimilesByArchivedStatus(boolean isArchived);

    List<FacsimileDto> getPaginatedFacsimiles(int page, int pageSize);
}
