package org.example.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис для сохранения файлов в хранилище MinIO и для получения файлов из хранилища MinIO.
 * Реализует интерфейс FileStorageService.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
    /**
     * Объект MinioClient из библиотеки MinIO, используемый для взаимодействия с хранилищем MinIO.
     */
    private final MinioClient minioClient;
    /**
     * Имя бакета (контейнера) MinIO, для файлов.
     */
    private final String bucketName;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;

    /**
     * Конструктор FileStorageServiceImpl.
     *
     * @param minioEndpoint  адрес (URL) MinIO сервера
     * @param minioAccessKey ключ доступа (логин) к MinIO серверу
     * @param minioSecretKey секретный ключ (пароль) для доступа к MinIO серверу
     * @param bucketName     имя бакета (контейнера) MinIO, в котором будет сохранен файл
     * @throws InvalidKeyException в случае ошибки при инициализации MinioClient
     */
    public FileStorageServiceImpl(
            @Value("${minio.endpoint}") String minioEndpoint,
            @Value("${minio.accessKey}") String minioAccessKey,
            @Value("${minio.secretKey}") String minioSecretKey,
            @Value("${minio.bucketName}") String bucketName
    ) throws InvalidKeyException {
        try {
            this.minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();
            this.bucketName = bucketName;
        } catch (Exception e) {
            throw new InvalidKeyException("Произошла ошибка при инициализации MinioClient" + e);
        }
        this.logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
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
            logger.info("Файл успешно сохранен на сервер MinIO в бакет с именем " + bucketName + ". UUID сохранённого файла: " + uuid);
            return ResponseEntity.ok(uuid);
        } catch (MinioException | IOException e) {
            logger.error("Ошибка при сохранении файла на сервер MinIO.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Ошибка при сохранении файла на сервер MinIO.");
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
            logger.error("Ошибка при создании бакета на сервере MinIO.");
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения файла из хранилища MinIO по-заданному UUID.
     * Возвращает ответ с файлом в виде ResponseEntity<Resource>.
     * Если файл не найден, возвращается статус "Not Found".
     * В методе выполняется поиск файла по UUID
     * и копирование содержимого файла в ByteArrayOutputStream,
     * который затем используется для создания InputStreamResource, возвращаемого в ответе.
     *
     * @param uuid UUID файла
     * @return ответ с файлом или статусом "Not Found", если файл не найден
     */
    @Override
    public ResponseEntity<Resource> getFile(String uuid) {
        try {
            Optional<String> objectName = getObjectFromMinio(uuid);
            if (objectName.isPresent()) {
                String fileName = objectName.get();
                // Проверяем, соответствует ли имя файла UUID
                if (fileName.matches(uuid)) {
                    GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build();
                    try (InputStream inputStream = minioClient.getObject(getObjectArgs);
                         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                        // Копируем InputStream в новый ByteArrayOutputStream
                        byte[] buffer = new byte[4096];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            byteArrayOutputStream.write(buffer, 0, length);
                        }
                        // Создаем новый ByteArrayInputStream из ByteArrayOutputStream
                        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
                            // Создаем новый InputStreamResource из нового ByteArrayInputStream
                            InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
                            return ResponseEntity.ok()
                                    .contentLength(byteArrayOutputStream.size())
                                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                    .body(inputStreamResource);
                        }
                    }
                }
            }
            return ResponseEntity.notFound().build();
        } catch (MinioException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для получения имени объекта (файла) из хранилища MinIO по UUID.
     * Если объект с указанным UUID найден, возвращается Optional с именем объекта.
     * Если объект не найден или возникла ошибка при взаимодействии с MinIO, возвращается пустой Optional.
     *
     * @param uuid UUID объекта (файла)
     * @return Optional с именем объекта или пустой Optional, если объект не найден или возникла ошибка
     */
    private Optional<String> getObjectFromMinio(String uuid) {
        try {
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .recursive(true)
                    .build();
            Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);
            String pattern = Pattern.quote(uuid);
            Pattern regex = Pattern.compile(pattern);
            for (Result<Item> result : results) {
                String objectName = result.get().objectName();
                Matcher matcher = regex.matcher(objectName);
                if (matcher.matches()) {
                    return Optional.of(objectName);
                }
            }
            // Если объект не найден, возвращаем пустой Optional
            return Optional.empty();
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            // В случае возникновения ошибки Minio, IO или других ошибок, возвращаем пустой Optional
            return Optional.empty();
        }
    }
}