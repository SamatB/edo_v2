package org.example.mappers;

import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.entity.Employee;
import org.example.entity.Nomenclature;
import org.example.entity.Question;
import org.example.entity.Region;
import org.example.mapper.AppealMapperImpl;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.NomenclatureMapper;
import org.example.mapper.QuestionMapper;
import org.example.mapper.RegionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.example.enums.StatusType.ON_THE_CARPET;

/**
 * Класс для тестирования маппинга Appeal в AppealDto и обратно
 */
public class AppealMapperTest {

    @InjectMocks
    private AppealMapperImpl appealMapper;

    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private NomenclatureMapper nomenclatureMapper;
    @Mock
    private RegionMapper regionMapper;
    @Mock
    private QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Метод для тестирования маппинга
     */
    @Test
    public void mappingTest() {

        Employee emp1 = new Employee();
        List<Employee> employees1 = new ArrayList<>();
        List<Employee> employees2 = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();
        Nomenclature nomenclature = new Nomenclature();
        Region region = new Region();

        Appeal appeal = Appeal.builder()
                .creationDate(ZonedDateTime.now())
                .registrationDate(ZonedDateTime.now())
                .archivedDate(null)
                .number("1")
                .reservedNumber("11")
                .annotation("ap1")
                .statusType(ON_THE_CARPET)
                .singers(employees1)
                .creator(emp1)
                .addressee(employees2)
                .nomenclature(nomenclature)
                .region(region)
                .questions(questionList)
                .build();

        AppealDto appealDto = appealMapper.entityToDto(appeal);

        Appeal appeal2 = appealMapper.dtoToEntity(appealDto);

        assertEquals(appeal, appeal2);
    }
}
