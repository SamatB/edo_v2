package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entity.Appeal;
import org.example.repository.AppealRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppealMapperResolver {
    private final AppealRepository appealRepository;

    public Appeal resolve(Long appealId) {
        return appealId != null ? appealRepository.findById(appealId).orElse(null) : new Appeal();
    }
}
