package org.example.mapper;

import org.example.dto.ResolutionDto;
import org.example.entity.Employee;
import org.example.entity.Resolution;
import org.example.entity.ResolutionReport;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между сущностью Resolution и объектом ResolutionDto.
 */
@Mapper(componentModel = "spring")
public interface ResolutionMapper extends AbstractMapper<Resolution, ResolutionDto> {


    ResolutionMapper INSTANCE = Mappers.getMapper(ResolutionMapper.class);

    @Override
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "signer.id", target = "signerId")
    @Mapping(source = "curator.id", target = "curatorId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "executors", target = "executorIds", qualifiedByName = "setEmpToSetLong")
    @Mapping(source = "resolutionReports", target = "resolutionReportIds", qualifiedByName = "setRepToSetLong")
    ResolutionDto entityToDto(Resolution resolution);

    @Named("setEmpToSetLong")
    static Set<Long> setEmpToSetLong(Set<Employee> employees) {
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
    }
    @Named("setRepToSetLong")
    static Set<Long> setRepToSetLong(Set<ResolutionReport> reports) {
        return reports.stream()
                .map(ResolutionReport::getId)
                .collect(Collectors.toSet());
    }


    @Override
    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(source = "signerId", target = "signer.id")
    @Mapping(source = "curatorId", target = "curator.id")
    @Mapping(source = "questionId", target = "question.id")
    @Mapping(source = "executorIds", target = "executors", qualifiedByName = "setLongToSetEmp")
    @Mapping(source = "resolutionReportIds", target = "resolutionReports", qualifiedByName = "setLongToSetRep")
    Resolution dtoToEntity(ResolutionDto resolutionDto);

    @Named("setLongToSetEmp")
    static Set<Employee> setLongToSetEmp(Set<Long> longsPongs) {
        return longsPongs.stream()
                .map(l -> Employee.builder().id(l).build())
                .collect(Collectors.toSet());
    }

    @Named("setLongToSetRep")
    static Set<ResolutionReport> setLongToSetRep(Set<Long> longsRep) {
        return longsRep.stream()
                .map(l -> ResolutionReport.builder().id(l).build())
                .collect(Collectors.toSet());
    }

}