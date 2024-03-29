/**
 * Маппер для преобразования между сущностью Appeal и объектом AppealDto.
 */

package org.example.mapper;

import org.example.dto.AppealDto;
import org.example.dto.QuestionDto;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, NomenclatureMapper.class, RegionMapper.class, QuestionMapper.class} )
public interface AppealMapper extends AbstractMapper<Appeal, AppealDto> {
}
