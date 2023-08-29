package org.example.mapper;

import org.example.dto.DepartmentDto;
import org.example.dto.NomenclatureDto;
import org.example.entity.Department;
import org.example.entity.Nomenclature;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface NomenclatureMapper extends AbstractMapper<Nomenclature, NomenclatureDto> {
}
