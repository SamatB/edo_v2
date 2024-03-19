/**
 * Репозиторий для работы с сущностью Appeal.
 */

package org.example.repository;

import org.example.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppealRepository extends JpaRepository <Appeal, Long> {
    List<Appeal> findAllByNumber(String number);
}
