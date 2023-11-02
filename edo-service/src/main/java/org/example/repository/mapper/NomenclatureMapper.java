package org.example.repository.mapper;

import org.example.dto.NomenclatureDto;
import org.example.entity.Nomenclature;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface NomenclatureMapper extends AbstractMapper<Nomenclature, NomenclatureDto> {
}
