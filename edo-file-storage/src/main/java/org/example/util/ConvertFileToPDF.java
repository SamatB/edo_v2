package org.example.util;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * Ультилита для конвертации различных типов файлов в PDF.
 * Поддерживает конвертацию DOC и DOCX документов, конвертацию PNG, JPEG и иобъединение изображений в PDF,
 * и слияние нескольких PDF файлов в один.
 */
@Log4j2
public class ConvertFileToPDF {

    public static InputStream convertToPDF(MultipartFile file) throws IOException {
        InputStream outInputStream = file.getInputStream();
        if (ValidatorFormatFile.isDocFile(file)) {
            log.info("Файл '{}' относится к формату DOC, DOCX", file.getOriginalFilename());
            outInputStream = docToPdf(file);
        } else if (ValidatorFormatFile.isAllImages(List.of(file))) {
            log.info("Файл {}' относится формату PNG, JPEG", file.getOriginalFilename());
            outInputStream = combineImagesIntoPDF(List.of(file));
        } else {
            log.info("Файл {}' не относится формату PNG, JPEG, DOC, DOCX", file.getOriginalFilename());
        }

        return outInputStream;
    }


    /**
     * Конвертирует файлы формата DOC и DOCX в PDF.
     *
     * @param file файл для конвертации.
     * @return поток данных с содержимым PDF.
     * @throws IOException если произошла ошибка при чтении или записи файлов.
     */

    public static InputStream docToPdf(MultipartFile file) throws IOException {
        log.info("Начало конвертации файла '{}' в PDF", file.getOriginalFilename());

        File docFile = converterToFile(file);
        log.debug("Файл '{}' был временно сохранен как '{}'", file.getOriginalFilename(), docFile.getAbsolutePath());

        try (InputStream doc = new FileInputStream(docFile); XWPFDocument document = new XWPFDocument(doc)) {

            PdfOptions options = PdfOptions.create();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(document, out, options);

            log.info("Файл '{}' успешно преобразован в PDF", file.getOriginalFilename());
            return new ByteArrayInputStream(out.toByteArray());
        } finally {
            boolean deleted = docFile.delete();
            if (deleted) {
                log.debug("Временный файл '{}' удален", docFile.getAbsolutePath());
            } else {
                log.warn("Не удалось удалить временный файл '{}'", docFile.getAbsolutePath());
            }
        }
    }

    /**
     * Объединяет несколько изображений в один PDF файл.
     *
     * @param files список изображений для объединения.
     * @return поток данных с содержимым PDF.
     * @throws IOException если произошла ошибка при чтении или записи файлов.
     */
    public static InputStream combineImagesIntoPDF(List<MultipartFile> files) throws IOException {
        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (MultipartFile input : files) {
                File imageFile = converterToFile(input);
                log.debug("Изображение '{}' было временно сохранено как '{}'", input.getOriginalFilename(), imageFile.getAbsolutePath());


                addImageAsNewPage(doc, imageFile);
                log.info("Изображение '{}' добавлено как новая страница в PDF", input.getOriginalFilename());

                boolean deleted = imageFile.delete();
                if (deleted) {
                    log.debug("Временный файл '{}' удален", imageFile.getAbsolutePath());
                } else {
                    log.warn("Не удалось удалить временный файл '{}'", imageFile.getAbsolutePath());
                }
            }
            doc.save(out);
            log.info("Преобразование изображения в PDF прошло успешно");
            return new ByteArrayInputStream(out.toByteArray());
        }
    }


    /**
     * Конвертирует {@link MultipartFile} во временный {@link File}.
     *
     * @param multipartFile файл для конвертации.
     * @return временный файл.
     * @throws IOException если произошла ошибка при записи файла.
     */
    private static File converterToFile(MultipartFile multipartFile) throws IOException {
        File convFile = File.createTempFile("temp", null);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }

    /**
     * Добавляет изображение как новую страницу в PDF документ.
     *
     * @param doc  документ, к которому добавляется страница.
     * @param file файл изображения для добавления.
     * @throws IOException если произошла ошибка при обработке изображения.
     */
    private static void addImageAsNewPage(PDDocument doc, File file) throws IOException {
        BufferedImage bImage = ImageIO.read(file);
        if (bImage != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos); // Преобразование в PNG
            byte[] data = bos.toByteArray();
            PDImageXObject image = PDImageXObject.createFromByteArray(doc, data, file.getName());
            PDRectangle pageSize = new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());

            PDPage page = new PDPage(pageSize);
            doc.addPage(page);
            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.drawImage(image, 0, 0, pageSize.getWidth(), pageSize.getHeight());
            }
        }
    }



}
