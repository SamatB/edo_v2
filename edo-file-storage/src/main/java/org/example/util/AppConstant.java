package org.example.util;

import java.util.Arrays;
import java.util.List;

/**
 * Интерфейс содержит константы, используемые для определения MIME-типов различных файлов.
 * Определяет типы для документов Word и изображений.
 */
public interface AppConstant {
    String DOC = "application/msword";
    String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    List<String> DOC_FILES = Arrays.asList(DOC, DOCX);
    String JPEG = "image/jpeg";
    String JPG = "image/jpg";
    String PNG = "image/png";
    List<String> IMAGES_ONLY = Arrays.asList( JPEG, JPG, PNG);
}
