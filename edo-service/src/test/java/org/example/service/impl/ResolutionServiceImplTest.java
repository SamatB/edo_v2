package org.example.service.impl;

import org.example.dto.ResolutionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

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
        try {
            resolutionService.validateResolution(resolutionDto1);
        } catch (Exception e) {
            fail();
        }

        // Некорректные значения - null
        ResolutionDto resolutionDto2 = new ResolutionDto();
        resolutionDto2.setType(null);
        resolutionDto2.setSerialNumber(null);
        resolutionDto2.setSignerId(null);
        try {
            resolutionService.validateResolution(resolutionDto2);
            fail();
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            assertEquals(4, message.split("не null").length);
            assertTrue(message.contains("Тип резолюции"));
            assertTrue(message.contains("Идентификатор подписанта"));
            assertTrue(message.contains("Серийный номер"));
        }

        // Некорректные значения
        Integer serialNumber3 = 0;
        Long signerId3 = -123_456_789_001L;
        ResolutionDto resolutionDto3 = new ResolutionDto();
        resolutionDto3.setType("wrong type");
        resolutionDto3.setSerialNumber(serialNumber3);
        resolutionDto3.setSignerId(signerId3);
        try {
            resolutionService.validateResolution(resolutionDto3);
            fail();
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            assertTrue(message.contains("значением ResolutionType"));
            assertEquals(3, message.split("должно быть положительным").length);
            assertTrue(message.contains("Идентификатор подписанта"));
            assertTrue(message.contains("Серийный номер"));
        }
    }
}
