package org.example.repository;

import org.example.entity.TaskForEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью TaskForEmployee.
 */
@Repository
public interface TaskForEmployeeRepository extends JpaRepository<TaskForEmployee, Long> {
}
