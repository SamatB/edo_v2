package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.feign.OverlayingFacsimileOnFileFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, который отвечает за работу метода по наложению факсимиле на файл
 */
@RestController
@RequestMapping("/overlay-facsimile-on-file")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Signing a file with facsimile")
public class OverlayingFacsimileOnFileController {

    private final OverlayingFacsimileOnFileFeignClient facsimileOnFileFeignClient;

    /**
     * Данный метод принимает на вход два параметра запроса:
     * первый - UUID MAIN файла,
     * второй - UUID факсимиле-изображения.
     * Факсимиле накладывается на правый нижний угол с отступом 200 пикселей снизу и 100 справа,
     * документ сохраняется обратно в MinIO в формате PDF.
     */
    @PostMapping
    @Operation(summary = "Сохраняет модифицированный файл обратно в MinIO")
    public ResponseEntity<String> signingFile(@RequestParam String fileUUID, @RequestParam String facsimileUUID) {
        facsimileOnFileFeignClient.signFile(fileUUID, facsimileUUID);
        log.info("Смодифицированный файл успешно сохранен на сервер MinIO.");
        return ResponseEntity
                .ok()
                .build();
    }
}
