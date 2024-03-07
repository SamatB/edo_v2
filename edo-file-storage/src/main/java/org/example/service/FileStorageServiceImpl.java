package org.example.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.example.util.ConvertFileToPDF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для сохранения, получения, удаления файлов в хранилище MinIO.
 * Реализует интерфейс FileStorageService.
 */
@Log4j2
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
            log.error("Ошибка при сохранении файла: file не может быть null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // Создание бакета в MinIO, если его не существует.
        createBuketInMinioIfNotExist(bucketName);
        // Сохранение файла в бакет MinIO
        try (InputStream inputStream = ConvertFileToPDF.docToPdf(file)) {
            String filename = file.getOriginalFilename();
            if (filename == null) {
                log.error("Ошибка при сохранении файла: имя файла не может быть null");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            String uuid = UUID.randomUUID().toString();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uuid)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("Файл успешно сохранен на сервер MinIO в бакет с именем " + bucketName + ". UUID сохранённого файла: " + uuid);
            return ResponseEntity.ok(uuid);
        } catch (MinioException | IOException e) {
            log.error("Ошибка при сохранении файла на сервер MinIO.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Ошибка при сохранении файла на сервер MinIO.");
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
                log.info("Бакет для хранения файлов с именем " + bucketName + " успешно создан на сервере MinIO");
            }
        } catch (Exception e) {
            log.error("Ошибка при создании бакета на сервере MinIO.");
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения файла из хранилища MinIO по-заданному UUID.
     *
     * @param uuid UUID файла на сервере MinIO
     * @return ResponseEntity.ok() с содержимым файла в случае успешного получения,
     * либо ResponseEntity.notFound() в случае отсутствия файла на сервере MInIO с указанным UUID,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    @Override
    public ResponseEntity<Resource> getFile(String uuid) {
        try {
            // Получение объекта (файла) из MinIO
            try (InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uuid)
                            .build()
            )) {
                // Чтение содержимого файла в массив байтов
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    IOUtils.copy(inputStream, outputStream);
                    // Создание ресурса с содержимым файла
                    byte[] fileBytes = outputStream.toByteArray();
                    Resource resource = new InputStreamResource(new ByteArrayInputStream(fileBytes));
                    // Возвращение ResponseEntity с содержимым файла
                    return ResponseEntity.ok()
                            .header("Content-Name", "UUID = \"" + uuid + "\"")
                            .contentLength(fileBytes.length)
                            .body(resource);
                }
            }
        } catch (ErrorResponseException e) {
            if (e.getMessage().equals("The specified key does not exist.")) {
                log.warn("Файла на сервере MinIO не существует по UUID: " + uuid);
                return ResponseEntity.notFound().build();
            } else {
                log.error("Ошибка при получении файла из сервера MinIO.");
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (MinioException | IOException e) {
            log.error("Ошибка при получении файла из сервера MinIO.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Ошибка при получении файла из сервера MinIO.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод для удаления файлов из хранилища MinIO по-заданным UUID(список UUID).
     *
     * @param uuidList список uuid, которые нужно удалить
     * @return ResponseEntity.HTTPStatus.ok  в случае успешного удаления,
     * либо ответ с HTTP статусом 500 в случае ошибки
     */
    @Override
    public ResponseEntity<UUID> deleteOldestThanFiveYearsFiles(@RequestBody List<UUID> uuidList) {
        for (UUID uuid : uuidList) {
            try {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(uuid.toString())
                        .build());
            } catch (ErrorResponseException e) {
                log.warn("Файла на сервере MinIO не существует по UUID: " + uuid);
                return ResponseEntity.notFound().build();
            } catch (MinioException | IOException e) {
                log.error("Ошибка при удалении файла из сервера MinIO");
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeyException e) {
                log.error("Ошибка при удалении файла из сервера MinIO");
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}