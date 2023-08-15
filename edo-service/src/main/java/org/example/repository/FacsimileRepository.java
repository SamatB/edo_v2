package org.example.repository;

import org.example.entity.Facsimile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacsimileRepository extends JpaRepository<Facsimile, Long> {

    /**
     * Поиск всех Facsimile в базе данных по статусу архивации.
     * @param isArchived - статус архивации.
     */
    List<Facsimile> findByArchived(boolean isArchived);

}
