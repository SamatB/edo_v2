package org.example.service.impl;

import org.example.dto.EmployeeDto;
import org.example.dto.ReportDto;
import org.example.mapper.ReportMapper;
import org.example.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {

//    @Mock
//    private ReportRepository reportRepository;
//
//    @Mock
//    private ReportMapper reportMapper;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveReport() {
        ReportDto reportDto = new ReportDto();
        ReportDto reportDto1 = reportService.saveReport(reportDto);
    }
}
