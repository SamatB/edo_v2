package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;
import org.example.repository.DepartmentRepository;
import org.example.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с сущностью Department.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AddressServiceImpl addressDetailService;
    private final DepartmentMapper departmentMapper;

    /**
     * Сохраняет департамент в базе данных.
     *
     * @param departmentDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = departmentMapper.dtoToEntity(departmentDto);
        Department parentDepartment = department.getDepartment();

        if (parentDepartment != null) {
            parentDepartment.setAddressDetails(addressDetailService.saveAddress(parentDepartment.getAddressDetails()));
            parentDepartment = departmentRepository.save(parentDepartment);
            log.info("В базу данных сохранен объект Department: {}", parentDepartment.getFullName());
        }

        department.setAddressDetails(addressDetailService.saveAddress(department.getAddressDetails()));
        department.setDepartment(parentDepartment);

        Department savedDepartment = departmentRepository.save(department);
        log.info("В базу данных сохранен объект Department: {}", department.getFullName());

        return departmentMapper.entityToDto(savedDepartment);
    }
}
