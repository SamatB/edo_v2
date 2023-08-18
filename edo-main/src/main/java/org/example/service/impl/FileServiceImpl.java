package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.example.dto.FilePoolDto;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.example.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileFeignClient fileFeignClient;
    private final FilePoolFeignClient filePoolFeignClient;

    /**
     * Метод для сохранения MultipartFile в файловом хранилище MinIO
     * Также сохраняет информацию о загруженном файле FilePool в базе данных
     *
     * @param multipartFile файл, который нужно сохранить
     * @return ResponseEntity<String> ResponseEntity со значением UUID сохраненного файла
     */
    @Override
    public ResponseEntity<String> saveFile(MultipartFile multipartFile) {
        log.info("Передача файла для сохранения в базу данных...");
        ResponseEntity<String> response = fileFeignClient.saveFile(multipartFile);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        log.info("Файл сохранен и ему присвоен UUID " + response.getBody());

        FilePoolDto filePoolDto = FilePoolDto.builder()
                .storageFileId(UUID.fromString(response.getBody()))
                .name(multipartFile.getOriginalFilename())
                .extension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                .size(multipartFile.getSize())
                .pageCount(1)
                .uploadDate(ZonedDateTime.now())
                .build();

        log.info("Передача FilePoolDto для сохранения в базу данных...");
        filePoolFeignClient.saveFile(filePoolDto);
        log.info("FilePoolDto успешно сохранен");
        return response;
    }
}
