package org.example.repository;

import org.example.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью FilePool.
 */
public interface FilePoolRepository extends JpaRepository<FilePool, Long> {
}
