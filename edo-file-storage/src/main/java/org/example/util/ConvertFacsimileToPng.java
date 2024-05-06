package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.utils.FileHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Утилитный класс для конвертации изображения в PNG с прозрачностью.
 * Честно стырено, переведено, адаптировано и доработано.
 * Вкратце, сначала проходим по всем пикселям и вычисляем значение серого для всего изображения.
 * Цвет контура глубокий, значение в оттенках серого маленькое.
 * Цвет фона светлый, значение в оттенках серого большое.
 * После средневзвешенного значения общее среднее значение будет немного ниже фонового значения в оттенках серого.
 * Затем снова проходим по всем пикселям и вычисляем оттенки серого для каждой точки пикселя.
 * Если ее значение в оттенках серого больше, чем grayMean, устанавливаем его альфа-значение равным 0, что является полностью прозрачным, в противном случае оно не будет обработано.
 */

@Slf4j
public class ConvertFacsimileToPng {
    public static final int INT = 0xff;

    public static InputStream convertToPng(MultipartFile file) throws IOException {
        log.info("Получено изображение для конвертации");
        try {
            BufferedImage bufferedImage = FileHelper.getConvertedBufferedImage(file);
            BufferedImage alphaImg = alphaProcess(bufferedImage);
            return getConvertedInputStream(alphaImg);
        } catch (Exception e) {
            log.error("Произошла ошибка при конвертации изображения в PNG", e);
            throw new IOException("Error converting image", e);
        }
    }

    /**
     * Метод для конвертации изображения в PNG формат с установленным прозрачным цветом на светлых пикселях.
     *
     * @param bufferedImage изображение для конвертации.
     * @return конвертированное изображение.
     */
    private static BufferedImage alphaProcess(BufferedImage bufferedImage) {
        log.info("Преобразование изображения в формат PNG с прозрачным фоном");
        if (bufferedImage.getColorModel().hasAlpha()) {
            log.info("Изображение уже в формате PNG");
            if (containsTransparencyAboveTenPercent(bufferedImage)) {
                log.info("Фон изображения прозрачный");
                return bufferedImage;
            }
            log.info("Фон изображения не прозрачный");
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage resImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        double grayMean = getGrayMean(bufferedImage);
        // Поправочный коэффициент для вычисления альфа-значения у изображений с "грязным" фоном
        double coef = grayMean / 100 * 11.1;
        grayMean = grayMean - coef;
        log.info("Установка альфа-значения в зависимости от средней яркости");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bufferedImage.getRGB(i, j);
                rgb = getAlpha(rgb, grayMean);
                resImage.setRGB(i, j, rgb);
            }
        }

        return resImage;
    }

    /**
     * Метод для вычисления средней яркости изображения.
     *
     * @param bufferedImage изображение для вычисления средней яркости.
     * @return средняя яркость.
     */
    private static double getGrayMean(BufferedImage bufferedImage) {
        log.info("Вычисление среднего уровня серого");
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double grayMean = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bufferedImage.getRGB(i, j);
                int r = (INT & rgb);
                int g = (INT & (rgb >> 8));
                int b = (INT & (rgb >> 16));
                // Это формула для вычисления значения серого, которая используется в OpenCV
                grayMean += (r * 0.299 + g * 0.587 + b * 0.114);
            }
        }
        // Calculate average gray level
        return grayMean / (width * height);
    }

    /**
     * Метод для установки альфа-значения в зависимости от средней яркости.
     *
     * @param rgb      текущее значение цвета.
     * @param grayMean средняя яркость.
     * @return новое значение цвета.
     */
    private static int getAlpha(int rgb, double grayMean) {
        // Значение int равно 32 битам, которое хранится в java в порядке ABGR, т.е. alpha - это первые 8 бит, а r - последние 8 бит, поэтому вы можете получить значение rgb следующим образом
        int r = (INT & rgb);

        int g = (INT & (rgb >> 8));
        int b = (INT & (rgb >> 16));
        double gray = (r * 0.299 + g * 0.587 + b * 0.114);

        if (gray > grayMean) {
            rgb = r + (g << 8) + (b << 16);
        }

        return rgb;
    }

    /**
     * Метод для определения есть ли прозрачность у пикселей изображения.
     *
     * @param image изображение
     * @return true, если изображение с прозрачным фоном
     */
    private static boolean containsTransparencyAboveTenPercent(BufferedImage image) {
        log.info("Проверка изображения на прозрачность");
        double threshold = image.getHeight() * image.getWidth() * 0.1;
        int transparencyCounter = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (isTransparent(image, j, i)) {
                    transparencyCounter++;
                }
                if (transparencyCounter > threshold) {
                    log.info("Прозрачность изображения свыше 10%");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для определения прозрачности точки на изображении.
     *
     * @param image изображение
     * @param x     ряд точки
     * @param y     столбец точки
     * @return true, если точка прозрачная
     */
    private static boolean isTransparent(BufferedImage image, int x, int y) {
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) == 0x00;
    }

    /**
     * Метод для конвертации изображения в InputStream.
     *
     * @param bufferedImage конвертированное изображение.
     * @return InputStream с конвертированным изображением.
     */
    private static InputStream getConvertedInputStream(BufferedImage bufferedImage) {
        log.info("Конвертация изображения в InputStream");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, FileHelper.PNG_FILE_EXTENSION, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            log.error("Ошибка конвертации изображения в InputStream", e);
            throw new RuntimeException(e);
        }
    }
}
