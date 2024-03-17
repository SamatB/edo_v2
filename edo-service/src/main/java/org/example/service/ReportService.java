package org.example.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;

public interface ReportService {
    void writeAppealsToXlsx(String fileName);
    ByteArrayInputStream getAppealsXlsxReport();
}
