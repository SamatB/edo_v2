package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.example.dto.FilePoolDto;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.example.service.FileService;
import org.example.utils.FileHelper;
import org.example.utils.FilePoolType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileFeignClient fileFeignClient;
    private final FilePoolFeignClient filePoolFeignClient;
    private final Set<String> facsimileExtension = Set.of("image/jpeg", "image/jpg", "image/png");

    /**
     * Метод для сохранения MultipartFile в файловом хранилище MinIO
     * Также сохраняет информацию о загруженном файле FilePool в базе данных
     *
     * @param multipartFile файл, который нужно сохранить
     * @return ResponseEntity<String> ResponseEntity со значением UUID сохраненного файла
     */
    @Override
    public ResponseEntity<String> saveFile(MultipartFile multipartFile, FilePoolType fileType) {
        if ((fileType == FilePoolType.FACSIMILE) && (!validateFacsimileFile(multipartFile))) {
                log.warn("Факсимиле не прошел валидацию.");
                return new ResponseEntity<>("Факсимиле не прошел валидацию по типу или размеру!", HttpStatus.BAD_REQUEST);
            }
        // в случае успешного прохождения проверок сохраняем файл в хранилище и БД
        log.info("Передача файла для сохранения в базу данных...");
        ResponseEntity<String> response = fileFeignClient.saveFile(multipartFile, fileType);
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
                .archivedDate(ZonedDateTime.now())
                .fileType(fileType)
                .build();
        log.info("Передача FilePoolDto для сохранения в базу данных...");
        filePoolFeignClient.saveFile(filePoolDto);
        log.info("{} успешно сохранен", filePoolDto);
        return response;
    }

    /**
     * @param multipartFile - файл с filetype = "FACSIMILE" который необходимо провалидировать
     * @return boolean - прошел файл валидацию или нет
     */
    public boolean validateFacsimileFile(MultipartFile multipartFile) {
        if (facsimileExtension.contains(multipartFile.getContentType().toLowerCase(Locale.ROOT))) {
            BufferedImage bufferedImage = FileHelper.getConvertedBufferedImage(multipartFile);
            return (bufferedImage.getHeight() <= 100) && (bufferedImage.getWidth() <= 100);
        }
        return false;
    }
}



