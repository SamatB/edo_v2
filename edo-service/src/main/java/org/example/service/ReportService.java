package org.example.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    void writeAppealsToCsv(int offset, int size,String fileName);
    ByteArrayInputStream downloadAppealsCsvReport(int offset, int size);
}
