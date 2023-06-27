package org.example.service;

import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.errors.MinioException;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис для получения файлов из хранилища MinIO.
 * Реализует интерфейс GetFileStorageService.
 */
@Service
public class GetFileStorageServiceImpl implements GetFileStorageService {
    /**
     * Объект MinioClient из библиотеки MinIO, используемый для взаимодействия с хранилищем MinIO.
     */
    private final MinioClient minioClient;
    /**
     * Имя бакета (контейнера) MinIO, в котором сохранены файлы.
     */
    private final String bucketName;

    /**
     * Конструктор GetFileStorageServiceImpl.
     *
     * @param minioEndpoint  адрес (URL) MinIO сервера
     * @param minioAccessKey ключ доступа (логин) к MinIO серверу
     * @param minioSecretKey секретный ключ (пароль) для доступа к MinIO серверу
     * @param bucketName     имя бакета (контейнера) MinIO, в котором будет сохранен файл
     * @throws InvalidKeyException в случае ошибки при инициализации MinioClient
     */
    public GetFileStorageServiceImpl(
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
                // Проверяем, соответствует ли имя файла формату UUID
                boolean isUuidMatch = fileName.matches(uuid);
                if (isUuidMatch) {
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
     * Метод для получения объекта из хранилища MinIO по UUID.
     * Возвращает имя объекта или пустое значение, если объект не найден.
     *
     * @param uuid UUID объекта
     * @return имя объекта или пустое значение, если объект не найден
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
            return Optional.empty();
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}