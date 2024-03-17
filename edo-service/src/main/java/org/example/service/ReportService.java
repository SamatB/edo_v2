package org.example.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    void writeAppealsToXlsx(String fileName);
    ByteArrayInputStream getAppealsXlsxReport();
}
