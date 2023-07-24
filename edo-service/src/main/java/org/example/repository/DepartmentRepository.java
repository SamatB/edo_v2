package org.example.repository;

import org.example.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для работы с сущностью Department.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
