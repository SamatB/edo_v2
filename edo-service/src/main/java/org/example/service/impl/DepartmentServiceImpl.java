package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DepartmentDto;
import org.example.entity.Address;
import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;
import org.example.repository.DepartmentRepository;
import org.example.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с сущностью Department.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AddressDetailServiceImpl addressDetailService;
    private final DepartmentMapper departmentMapper;

    /**
     * Сохраняет департамент в базе данных.
     *
     * @param departmentDto объект DTO резолюции
     * @return сохраненный объект DTO резолюции
     */
    @Transactional
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = departmentMapper.dtoToEntity(departmentDto);
        // Проверяем, есть ли родительский департамент
        Department parentDepartment = department.getDepartment();
        if (parentDepartment != null) {
            // Если родительский департамент не сохранен, сохраняем его сначала
            // Проверяем, есть ли связанный AddressDetails с родительским департаментом
            Address addressDetailsParent = department.getDepartment().getAddressDetails();
            if (addressDetailsParent != null) {
                parentDepartment.setAddressDetails(addressDetailService.saveAddress(addressDetailsParent));
            }
            parentDepartment = departmentRepository.save(parentDepartment);
            log.info("В базу данных сохранен объект Department: " + parentDepartment.getFullName());
            department.setDepartment(parentDepartment);
        }
        // Проверяем, есть ли связанный AddressDetails
        Address addressDetails = department.getAddressDetails();
        if (addressDetails != null) {
            department.setAddressDetails(addressDetailService.saveAddress(addressDetails));
        }
        // Теперь сохраняем Department, и связанные с ним объекты (если есть)
        Department savedDepartment = departmentRepository.save(department);
        log.info("В базу данных сохранен объект Department: " + department.getFullName());
        return departmentMapper.entityToDto(savedDepartment);
    }
}
