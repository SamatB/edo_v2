package org.example.repository;

import org.example.entity.Author;
import org.example.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для работы с сущностью Department.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.fullName LIKE :search%")
    List<Department> searchByName(@Param("search") String search);

}
