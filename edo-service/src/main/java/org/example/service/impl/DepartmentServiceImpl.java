package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;
import org.example.repository.DepartmentRepository;
import org.example.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Поик департамента по имени.
     *
     * @param search строка символов для поиска департамента
     * @return возвращает список департаментов удовлетворяющих строке поиска
     */

    public List<DepartmentDto> getDepartmentByName(String search) {
        String fixedSearch = fixLayout(search);

        if (fixedSearch == null || fixedSearch.length() <= 3) {
            log.info("Строка поиска пуста или менее трех символов!");
            throw new EntityNotFoundException("Строка поиска пуста или менее трех символов!");
        }
        log.info("Начат поиск в БД по имени департамента: " + fixedSearch);
        return departmentRepository.searchByName(fixedSearch)
                .stream()
                .map(departmentMapper::entityToDto)
                .collect(Collectors.toList());

    }

    /**
     * Проверка расскладки клавиатуры(проверяет и при необходимости конвертирует строку в русские символы).
     *
     * @param input строка символов в русской или английской расскладке
     * @return возвращает строку в русской расскладке
     */

    public String fixLayout(String input) {
        final String ENGLISH_ALPHABET = "`qwertyuiop[]asdfghjkl;'zxcvbnm,.~QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>";
        final String RUSSIAN_ALPHABET = "ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
        StringBuilder result = new StringBuilder();

        char[] chars = input.toCharArray();
        for (char c : chars) {
            if (ENGLISH_ALPHABET.indexOf(c) != -1) {
                result.append(RUSSIAN_ALPHABET.charAt(ENGLISH_ALPHABET.indexOf(c)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
