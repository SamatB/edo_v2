package org.example.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    void writeAppealsToCsv(String fileName);
    ByteArrayInputStream downloadAppealsCsvReport();
}
