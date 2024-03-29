package org.example.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    void writeAppealsToXlsx(int offset, int size, String fileName);
    ByteArrayInputStream getAppealsXlsxReport(int offset, int size);
}
