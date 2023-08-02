/**
 * Сервис для работы с Employee
 */

package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

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
        return Optional.ofNullable(username)
                .map(employeeRepository::findByUsername)
                .map(employeeMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка поиска: работник с username: " + username + " не найден"));
    }

    /**
     * Получает работника из базы данных по id.
     *
     * @param id - пользовательское имя работника.
     * @return объект DTO работника.
     */
    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка поиска: работник с id: %s не найден", id)));
    }

    /**
     * Сохраняет работника в базу данных.
     *
     *  @param employeeDto - логин работника.
     * @return объект DTO работника.
     */

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        return Optional.ofNullable(employeeDto)
                .map(employeeMapper::dtoToEntity)
                .map(employee -> {
                    employee.setCreationDate(ZonedDateTime.now());
                    return employee;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения работника: работник не должен быть null"));
    }
}
