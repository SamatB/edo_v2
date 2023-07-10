package org.example.repository;

import org.example.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Resolution.
 */
@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
}
