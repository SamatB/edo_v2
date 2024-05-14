package org.example.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.example.service.FileStorageService;
import org.example.service.OverlayingFacsimileOnFileService;
import org.example.util.exception.EmptyValueException;
import org.example.utils.FilePoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@Log4j2
public class OverlayingFacsimileOnFileImpl implements OverlayingFacsimileOnFileService {

    private final FileStorageService fileStorageService;

    @Autowired
    public OverlayingFacsimileOnFileImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Метод для преобразования модифицированного pdf MAIN файла в MultipartFile
     */
    private static MultipartFile convertToMultipartFile(byte[] file, String fileName) throws IOException {
        return new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "application/pdf";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return file.length;
            }

            @Override
            public byte[] getBytes() {
                return file;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(file);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try (FileOutputStream fos = new FileOutputStream(dest)) {
                    fos.write(file);
                }
            }
        };
    }

    @Override
    public ResponseEntity<String> overlayFacsimileOnFile(String fileUUID, String facsimileUUID) {
        if (facsimileUUID == null || fileUUID == null || facsimileUUID.isEmpty() || fileUUID.isEmpty()) {
            log.warn("Введите UUID файла или факсимиле");
            throw new EmptyValueException("Не предоставлены ключи UUID файла или факсимиле");
        }
        //Получение MAIN файла из MinIO
        Resource mainFile = fileStorageService.getFile(fileUUID).getBody();
        //Получение факсимиле-изображение из MinIO
        Resource facsimile = fileStorageService.getFile(facsimileUUID).getBody();
        try {
            if (mainFile != null && facsimile != null) {
                //Загрузка pdf документ
                PDDocument document = PDDocument.load(mainFile.getInputStream());
                //Считывание байтов факсимиле
                byte[] pngBytes = facsimile.getInputStream().readAllBytes();
                //Загрузка факсимиле-изображение
                BufferedImage facsimileImage = ImageIO.read(new ByteArrayInputStream(pngBytes));

                //Итерация каждой страницы PDF документа
                for (int pageNum = 0; pageNum < document.getNumberOfPages(); pageNum++) {
                    PDPage page = document.getPage(pageNum);
                    PDRectangle pageSize = page.getMediaBox();

                    //Факсимиле накладывается на правый нижний угол с отступом 200 пикселей снизу
                    float facsimileY = 200;
                    //и 100 справа
                    float facsimileX = pageSize.getWidth() - facsimileImage.getWidth() - 100;
                    //Создание потока контента для страницы
                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, pngBytes, "png");
                        contentStream.drawImage(pdImage, facsimileX, facsimileY);
                    }
                }
                //Сохранение модифицированного PDF документа в byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);
                document.close();
                //Конвертирование сохраненного модифицированного PDF документа в MultipartFile
                MultipartFile multipartFile = convertToMultipartFile(byteArrayOutputStream.toByteArray(), "signed_main_document.pdf");
                log.info("Сохранение модифицированного PDF документа обратно в MinIO");
                //Сохранение модифицированного PDF документа обратно в MinIO
                fileStorageService.saveFile(multipartFile, FilePoolType.MAIN);
            }else {
                throw new EmptyValueException("Ошибка при получении файлов из MinIO");
            }
        } catch (IOException e) {
            log.warn("Ошибка в : {0}", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
