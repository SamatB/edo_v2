package org.example.service;

import org.example.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    String fixLayout(String input);

    List<DepartmentDto> getDepartmentByName(String search);

}
