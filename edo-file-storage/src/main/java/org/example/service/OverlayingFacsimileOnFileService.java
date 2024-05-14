package org.example.service;

import org.springframework.http.ResponseEntity;

/**
 *  Интерефейс, описывающий метод по налажению факсимиле-изображение на MAIN файл
 */
public interface OverlayingFacsimileOnFileService {
   ResponseEntity<String> overlayFacsimileOnFile(String fileUUID, String facsimileUUID);
}
