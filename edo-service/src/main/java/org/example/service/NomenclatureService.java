package org.example.service;

import org.example.dto.NomenclatureDto;

import java.util.List;

public interface NomenclatureService {
    NomenclatureDto saveNomenclature(NomenclatureDto nomenclatureDto);
    void deleteNomenclature(Long id);
    List<NomenclatureDto> getPaginatedNomenclature(int offset, int size);

    void ArchiveNomenclature(Long id, boolean switcher);

    List<NomenclatureDto> getArchivedOrNoArchivedNomenclature(boolean isArchivedOrNonArchived);
}
