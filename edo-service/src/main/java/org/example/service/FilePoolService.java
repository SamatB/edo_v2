package org.example.service;

import org.example.dto.FilePoolDto;

import java.util.List;
import java.util.UUID;

public interface FilePoolService {

    FilePoolDto saveFilePool(FilePoolDto filePoolDto);

    List<UUID> getUUIDByCreationDateBeforeFiveYears();
}
