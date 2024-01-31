package org.example.service.impl;

import org.example.dto.ResolutionDto;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResolutionServiceImplTest {

    @InjectMocks
    private ResolutionServiceImpl resolutionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для метода validateResolution
     */
    @Test
    void validateResolution() {
        // Корректные данные
        ResolutionDto resolutionDto1 = new ResolutionDto();
        resolutionDto1.setType("REQUEST");
        resolutionDto1.setSerialNumber(123);
        resolutionDto1.setSignerId(123_456_789_001L);

        Map<String, String> map1 = resolutionService.validateResolution(resolutionDto1);

        assertTrue(map1.isEmpty());

        // Некорректные значения
        ResolutionDto resolutionDto2 = new ResolutionDto();
        resolutionDto2.setType("request");
        resolutionDto2.setSerialNumber(0);
        resolutionDto2.setSignerId(-123_456_789_001L);

        Map<String, String> map2 = resolutionService.validateResolution(resolutionDto2);

        assertEquals(3, map2.size());

        // Некорректные значения - null
        ResolutionDto resolutionDto3 = new ResolutionDto();
        resolutionDto3.setType(null);
        resolutionDto3.setSerialNumber(null);
        resolutionDto3.setSignerId(null);

        Map<String, String> map3 = resolutionService.validateResolution(resolutionDto3);

        assertEquals(3, map3.size());
    }
}
