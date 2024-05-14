package org.example.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.service.OverlayingFacsimileOnFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, который отвечает за наложение факсимиле на файл
 */
@RestController
@RequestMapping("/overlay-facsimile-on-file")
@Log4j2
@AllArgsConstructor
@Api(value = "Контроллер для наложения факсимиле на файл")
public class OverlayingFacsimileOnFileController {

    private final OverlayingFacsimileOnFileService overlayingFacsimileOnFileService;

    /**
     * Данный метод принимает на вход UUID MAIN файла и UUID факсимиле-изображения,
     * факсимиле накладывается на правый нижний угол с отступом 200 пикселей снизу и 100 справа,
     * документ сохраняется обратно в MinIO в формате PDF
     */
    @PostMapping
    public ResponseEntity<String> signingFile(@RequestParam String fileUUID, @RequestParam String facsimileUUID) {
        try {
            ResponseEntity<String> responseEntity = overlayingFacsimileOnFileService.overlayFacsimileOnFile(fileUUID, facsimileUUID);
            log.info("Файл успешно сохранен на сервер MinIO.");
            return responseEntity;
        } catch (Exception e) {
            log.error("Ошибка при сохранении файла на сервер MinIO.", e);
            return ResponseEntity.status(500).build();
        }
    }
}
