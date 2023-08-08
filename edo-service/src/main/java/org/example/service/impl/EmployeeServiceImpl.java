/**
 * Сервис для работы с Employee
 */

package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.entity.Address;
import org.example.mapper.EmployeeMapper;
import org.example.repository.AddressRepository;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AddressRepository addressRepository;

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
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        return Optional.ofNullable(employeeDto)
                .map(employeeMapper::dtoToEntity)
                .map(employee -> {
                    Address savedAddress = addressRepository.save(employee.getAddressDetails());
                    log.info("Address {} was saved!", savedAddress);
                    employee.setAddressDetails(savedAddress);
                    return employee;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения работника: работник не должен быть null"));
    }
}
