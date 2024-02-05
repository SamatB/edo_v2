package org.example.service.impl;

import org.example.dto.ResolutionDto;
import org.example.dto.ResolutionReportDto;
import org.example.entity.Resolution;
import org.example.entity.ResolutionReport;
import org.example.mapper.ResolutionReportMapper;
import org.example.repository.ResolutionReportRepository;
import org.example.repository.ResolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса ResolutionReportServiceImpl
 */
class ResolutionReportServiceImplTest {

    @Mock
    private ResolutionReportMapper resolutionReportMapper;

    @Mock
    private ResolutionRepository resolutionRepository;

    @Mock
    private ResolutionReportRepository resolutionReportRepository;

    @InjectMocks
    private ResolutionReportServiceImpl resolutionReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveResolutionReport() {
        // Отчет - null
        ResolutionReportDto resolutionReportDto1 = null;
        assertThrows(IllegalArgumentException.class, () -> resolutionReportService.saveResolutionReport(resolutionReportDto1));

        // Ссылка на резолюцию в отчете - null
        ResolutionReportDto resolutionReportDto2 = new ResolutionReportDto();
        assertThrows(IllegalArgumentException.class, () -> resolutionReportService.saveResolutionReport(resolutionReportDto2));

        // Некорректный id резолюции
        ResolutionReportDto resolutionReportDto3 = new ResolutionReportDto();
        resolutionReportDto3.setResolution(new ResolutionDto());
        resolutionReportDto3.getResolution().setId(Long.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> resolutionReportService.saveResolutionReport(resolutionReportDto3));

        // Успешное сохранение отчета
        ResolutionReportDto resolutionReportDto4 = new ResolutionReportDto();
        ResolutionDto resolutionDto4 = new ResolutionDto();
        resolutionDto4.setId(Long.MAX_VALUE);
        resolutionReportDto4.setResolution(resolutionDto4);
        Resolution resolution = new Resolution();
        ResolutionReport resolutionReport = new ResolutionReport();
        resolution.setResolutionReports(new HashSet<>());
        when(resolutionRepository.findById(anyLong())).thenReturn(Optional.of(resolution));
        when(resolutionReportMapper.dtoToEntity(any())).thenReturn(resolutionReport);
        when(resolutionReportRepository.save(any())).thenReturn(resolutionReport);
        when(resolutionRepository.save(any())).thenReturn(resolution);
        when(resolutionReportMapper.entityToDto(any())).thenReturn(resolutionReportDto4);

        resolutionReportService.saveResolutionReport(resolutionReportDto4);

        verify(resolutionReportMapper).entityToDto(any());
    }
}
