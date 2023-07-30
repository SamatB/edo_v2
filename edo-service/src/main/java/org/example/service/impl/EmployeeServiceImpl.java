/**
 * Сервис для работы с Employee
 */

package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Получает работника из базы данных по username.
     *
     * @param username - пользовательское имя работника.
     * @return объект DTO работника.
     */
    @Override
    public EmployeeDto getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee != null) {
            return employeeMapper.entityToDto(employee);
        }
        throw new EntityNotFoundException("Ошибка поиска: работник с username: " + username + " не найден");
    }

    /**
     * Получает работника из базы данных по id.
     *
     * @param id - пользовательское имя работника.
     * @return объект работника.
     */
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка поиска работника с id: %s не найден", id)));
    }
}
