package org.example.service.impl;

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

    @Mock
    private ResolutionRepository resolutionRepository;

    @Mock
    private ResolutionMapper resolutionMapper;

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
        String resolutionDtoString1 ="{\"type\": \"REQUEST\",\"signerId\": 987123456789,\"serialNumber\": 12}";
        Map<String, String> map1 = resolutionService.validateResolution(resolutionDtoString1);
        assertTrue(map1.isEmpty());

        // Корректные данные
        String resolutionDtoString2 ="{\"type\": \"DIRECTION\",\"signerId\": 12,\"serialNumber\": 12}";
        Map<String, String> map2 = resolutionService.validateResolution(resolutionDtoString2);
        assertTrue(map2.isEmpty());

        // Некорректные данные
        String resolutionDtoString3 ="{\"type\": \"abc\",\"signerId\": -12,\"serialNumber\": \"abc\"}";
        Map<String, String> map3 = resolutionService.validateResolution(resolutionDtoString3);
        assertEquals(3, map3.size());

        // Некорректные данные
        String resolutionDtoString4 ="{\"typo\": \"DIRECTION\",\"signer_Id\": 13,\"SERIALNUMBER\": 12}";
        Map<String, String> map4 = resolutionService.validateResolution(resolutionDtoString4);
        assertEquals(3, map4.size());

        // Некорректные данные
        String resolutionDtoString5 ="xyz";
        Map<String, String> map5 = resolutionService.validateResolution(resolutionDtoString5);
        assertEquals(1, map5.size());
        assertTrue(map5.containsKey("json"));
    }
}
