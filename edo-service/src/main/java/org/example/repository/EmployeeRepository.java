/**
 * Репозиторий Employee
 */

package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Поиск Employee в базе данных по username.
     * @param username - имя пользователя.
     */
    Employee findByUsername(String username);
}
