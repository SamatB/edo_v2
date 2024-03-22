package org.example.mapper;

import org.example.dto.ResolutionDto;
import org.example.entity.BaseEntity;
import org.example.entity.Employee;
import org.example.entity.Question;
import org.example.entity.Resolution;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между сущностью Resolution и объектом ResolutionDto.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ResolutionMapper extends AbstractMapper<Resolution, ResolutionDto> {
    ResolutionMapper INSTANCE = Mappers.getMapper(ResolutionMapper.class);

    @Override
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "signer.id", target = "signerId")
    @Mapping(source = "curator.id", target = "curatorId")
    @Mapping(source = "question.id", target = "questionId")

    @Mapping(source = "executors", target = "executorIds", qualifiedByName = "setEmpToSetLong")
    ResolutionDto entityToDto(Resolution resolution);

    @Override
    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(source = "signerId", target = "signer.id")
    @Mapping(source = "curatorId", target = "curator.id")
    @Mapping(source = "questionId", target = "question.id")

    @Mapping(source = "executorIds", target = "executors", qualifiedByName = "setLongToSetEmp")
    Resolution dtoToEntity(ResolutionDto resolutionDto);



//    @BeforeMapping
//    protected void setToLong(Resolution resolution, @MappingTarget ResolutionDto resolutionDto) {
//        resolutionDto.setExecutorIds(resolution.getExecutors().stream()
//                .map(Employee::getId)
//                .collect(Collectors.toSet()));
//    }


//    @Mapping(source = "executors", target = "executorIds", qualifiedByName = "setEmpToSetLong")
//    public ResolutionDto resolutionDtoMapper(Resolution resolution);

    @Named("setEmpToSetLong")
    static Set<Long> setEmpToSetLong(Set<Employee> employees) {
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
    }

//    @Mapping(source = "executorIds", target = "executors", qualifiedByName = "setLongToSetEmp")
//    public Resolution resolutionMapper(ResolutionDto resolutionDto);


    @Named("setLongToSetEmp")
    static Set<Employee> setLongToSetEmp(Set<Long> longs) {
        return longs.stream().map(l -> Employee.builder().id(l).build()).collect(Collectors.toSet());
    }

}