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

import java.util.List;
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
     * Получает работников из базы данных по id.
     *
     * @param ids - Коллекция id работников.
     * @return объект работника.
     */
    public List<EmployeeDto> getEmployeesByIds(List<Long> ids) {
        if(ids.isEmpty()) {
             throw new IllegalArgumentException("Коллекция id пользователей не должно быть null");
        }
        return employeeRepository.findAll().stream()
                .filter(employee -> ids.contains(employee.getId()))
                .map(employeeMapper::entityToDto)
                .toList();
    }
}
