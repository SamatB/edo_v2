package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FilePoolDto;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileFeignClient fileFeignClient;
    private final FilePoolFeignClient filePoolFeignClient;

    /**
     * Метод для сохранения MultipartFile в файловом хранилище MinIO
     * Также сохраняет информацию о загруженном файле FilePool в базе данных
     *
     * @param multipartFile файл, который нужно сохранить
     * @return ResponseEntity<String> ResponseEntity со значением UUID сохраненного файла
     */
    public ResponseEntity<String> saveFile(MultipartFile multipartFile) {
        log.info("Передача файла для сохранения в базу данных...");
        ResponseEntity<String> response = fileFeignClient.saveFile(multipartFile);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        log.info("Файл сохранен и ему присвоен UUID " + response.getBody());
        // Получение названия файла
        String originalFilename = multipartFile.getOriginalFilename();
        // Получение расширения файла
        String fileExtension = "";
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex != -1) {
                fileExtension = originalFilename.substring(lastDotIndex + 1);
            }
        }
        FilePoolDto filePoolDto = FilePoolDto.builder()
                .storageFileId(UUID.fromString(response.getBody()))
                .name(originalFilename)
                .extension(fileExtension)
                .size(multipartFile.getSize())
                .pageCount(1)
                .uploadDate(ZonedDateTime.now())
                .build();

        log.info("Передача FilePoolDto для сохранения в базу данных...");
        filePoolFeignClient.saveFile(filePoolDto);
        return response;
    }
}
