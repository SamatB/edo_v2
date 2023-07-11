package org.example.repository;

import org.example.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppealRepository extends JpaRepository <Appeal, Long> {
}
