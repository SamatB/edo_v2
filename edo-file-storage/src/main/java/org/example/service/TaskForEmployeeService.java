package org.example.service;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *  Интерефейс описывающий метод по формированию PDF файла задания для сотрудника
 */
public interface TaskForEmployeeService {

    ByteArrayResource generateTaskForEmployeeIntoPDF(TaskForEmployeeDto task) throws IOException;

    BufferedImage getFacsimileImageFromMinIO(Resource resource) throws IOException;

    String getFacsimileFileUUID();
}
