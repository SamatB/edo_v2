package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.impl.FileServiceImpl;
import org.example.utils.FilePoolType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер для работы с MultipartFile.
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File", description = "API for save files to DB and Remote Storage")
public class FileController {

    private final FileServiceImpl fileService;
    /**
     * Сохраняет MultipartFile в файловое хранилище MinIO
     * Сохраняет информацию о загруженном файле FilePool в базе данных
     *
     * @param multipartFile файл, который нужно сохранить
     * @return ResponseEntity<String> ResponseEntity со значением UUID сохраненного файла
     */
    @PostMapping
    @Operation(summary = "Сохраняет новый файл в базу данных")
    @ApiResponse()
    public ResponseEntity<String> saveFile(
            @RequestPart("file") MultipartFile multipartFile,
            @Parameter(
                    name = "filetype",
                    description = "Filetype of saving files. There are two main types: 'MAIN' and 'FACSIMILE' ",
                    example = "FACSIMILE",
                    required = true
            )
            @RequestParam FilePoolType filetype) {
        if (multipartFile.isEmpty()) {
            log.warn("Пришел пустой MultipartFile.");
            return new ResponseEntity<>("FIle is empty.",HttpStatus.BAD_REQUEST);
        }
        log.info("MultipartFile успешно принят и отправлен в сервис-слой.");
        return fileService.saveFile(multipartFile, filetype);
    }
}
