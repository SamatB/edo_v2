/**
 * Сервис для работы с Employee
 */

package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.AppealDto;
import org.example.dto.EmployeeDto;
import org.example.entity.Appeal;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
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

    /**
     * Сохранение работника в БД.
     * Метод выполняет сохранение обращения используя EmployeeRepository.
     *
     * @param employeeDto объект DTO с новыми данными работника, которые требуется сохранить в базе данных.
     * @return объект DTO работника.
     */

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        try {
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.entityToDto(savedEmployee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка сохранения обращения: обращение не должно быть null");
        }
    }
}
