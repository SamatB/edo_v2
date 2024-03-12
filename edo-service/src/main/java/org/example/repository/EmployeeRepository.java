/**
 * Репозиторий Employee
 */

package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Поиск Employee в базе данных по username.
     * @param username - имя пользователя.
     */
    Employee findByUsername(String username);


    /**
     * Поиск Employee в базе данных по changedName в нижнем регистре,
     * с учётом, что е==ё.
     * @param changedName - строка с символами для поиска сотрудников по FIO.
     */
    @Query(value = "SELECT * FROM \"edo-2\".employee WHERE LOWER(REPLACE(fio_dative, 'ё', 'e')) LIKE LOWER(CONCAT('%', :changedName, '%')) " +
            "OR LOWER(REPLACE(fio_genitive, 'ё', 'e')) LIKE LOWER(CONCAT('%', :changedName, '%')) " +
            "OR LOWER(REPLACE(fio_nominative, 'ё', 'e')) LIKE LOWER(CONCAT('%', :changedName, '%'))",
        nativeQuery = true)

    List<Employee> findEmployeeSearchByText(@Param("changedName") String changedName);
}
