package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер для работы с MultipartFile.
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File")
public class FileController {

    private final FileService fileService;
    /**
     * Сохраняет MultipartFile в файловое хранилище MinIO
     * Сохраняет информацию о загруженном файле FilePool в базе данных
     *
     * @param multipartFile файл, который нужно сохранить
     * @return ResponseEntity<String> ResponseEntity со значением UUID сохраненного файла
     */
    @PostMapping
    @Operation(summary = "Сохраняет новый файл в базу данных")
    public ResponseEntity<String> saveFile(
            @RequestPart("file") MultipartFile multipartFile) {
        return fileService.saveFile(multipartFile);
    }
}
