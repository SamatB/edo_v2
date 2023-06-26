package org.example.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Сервис для сохранения файлов в хранилище MinIO.
 * Реализует интерфейс SaveFileStorageService.
 */
@Service
public class SaveFileStorageServiceImpl implements SaveFileStorageService {
    /**
     * Объект MinioClient из библиотеки MinIO, используемый для взаимодействия с хранилищем MinIO.
     */
    private final MinioClient minioClient;
    /**
     * Имя бакета (контейнера) MinIO, в котором будут сохранены файлы.
     */
    private final String bucketName;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;

    /**
     * Конструктор SaveFileStorageServiceImpl.
     *
     * @param minioEndpoint  адрес (URL) MinIO сервера
     * @param minioAccessKey ключ доступа (логин) к MinIO серверу
     * @param minioSecretKey секретный ключ (пароль) для доступа к MinIO серверу
     * @param bucketName     имя бакета (контейнера) MinIO, в котором будет сохранен файл
     */
    public SaveFileStorageServiceImpl(
            @Value("${minio.endpoint}") String minioEndpoint,
            @Value("${minio.accessKey}") String minioAccessKey,
            @Value("${minio.secretKey}") String minioSecretKey,
            @Value("${minio.bucketName}") String bucketName
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
        this.bucketName = bucketName;
        this.logger = LoggerFactory.getLogger(SaveFileStorageServiceImpl.class);
    }

    /**
     * Метод для сохранения файла в MinIO.
     * Он проверяет, существует ли бакет с указанным именем в MinIO, и если нет, то создает его.
     * Затем он генерирует уникальный идентификатор (UUID) для сохраняемого файла,
     * сохраняет файл в указанном бакете и возвращает UUID в ответе.
     * Если происходит ошибка в процессе сохранения файла,
     * возвращается ответ с HTTP статусом 500 (внутренняя ошибка сервера).
     *
     * @param file загружаемый файл
     * @return ответ с UUID сохраненного файла в случае успешного сохранения,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    @Override
    public ResponseEntity<String> saveFile(MultipartFile file) {
        // Проверка на null для параметра file
        if (file == null) {
            logger.warn("Ошибка при сохранении файла: file не может быть null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // Создание бакета в MinIO, если его не существует.
        createBuketInMinioIfNotExist(bucketName);
        // Сохранение файла в бакет MinIO
        try (InputStream inputStream = file.getInputStream()) {
            String filename = file.getOriginalFilename();
            assert filename != null;
            String uuid = UUID.randomUUID().toString();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uuid)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            logger.info("Файл успешно сохранен на сервере MinIO в бакет с именем " + bucketName + ". UUID сохранённого файла: " + uuid);
            return ResponseEntity.ok(uuid);
        } catch (MinioException | IOException e) {
            logger.error("Ошибка при сохранении файла.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Ошибка при сохранении файла.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для создания бакета (контейнера) в MinIO, если его не существует.
     * Он проверяет, существует ли бакет с указанным именем в MinIO, и если нет, то создает его.
     *
     * @param bucketName имя создаваемого бакета
     */
    public void createBuketInMinioIfNotExist(String bucketName) {
        try {
            if (!minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName).build()
            )) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName).build()
                );
                logger.info("Бакет для хранения файлов с именем " + bucketName + " успешно создан на сервере MinIO");
            }
        } catch (Exception e) {
            logger.error("Ошибка при создании бакета в MinIO.");
            e.printStackTrace();
        }
    }
}