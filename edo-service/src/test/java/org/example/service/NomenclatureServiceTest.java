/**
 * Тесты для класса NomenclatureService.
 */
package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.example.entity.Nomenclature;
import org.example.mapper.NomenclatureMapper;
import org.example.repository.NomenclatureRepository;
import org.example.service.impl.NomenclatureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NomenclatureServiceTest {

    @InjectMocks
    private NomenclatureServiceImpl nomenclatureService;

    @Mock
    private NomenclatureRepository nomenclatureRepositoryMock;

    @Mock
    private NomenclatureMapper nomenclatureMapperMock;

    @Mock
    private DepartmentService departmentServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода generateNumberForAppeal.
     */
    @Test
    void testGenerateNumberForAppeal() {
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setIndex("СОГЛ");
        nomenclature.setTemplate("%ИНДЕКС-%ГОД/2-%НОМЕР");
        nomenclature.setCurrentValue(1L);

        String generatedNumber = nomenclatureService.generateNumberForAppeal(nomenclature);

        assertEquals("СОГЛ-2024/2-1", generatedNumber);
    }
}