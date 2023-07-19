/**
 * Сервис для работы с Employee
 */

package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Получает работника из базы данных по username.
     *
     * @param username - пользовательское имя работника.
     * @return объект DTO работника.
     */
    public EmployeeDto getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee != null) {
            return employeeMapper.entityToDto(employee);
        }
        throw new EntityNotFoundException("Ошибка поиска: работник с username: " + username + " не найден");
    }
}