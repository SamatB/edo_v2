package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.feign.FileFeignClient;
import org.example.feign.FilePoolFeignClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для получения работы с FilePool и FileStorage.
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class CleanFileStorageService {
    private final FileFeignClient fileFeignClient;
    private final FilePoolFeignClient filePoolFeignClient;

    /**
     * Метод для получения списка UUID из FilePool и удаления файлов из MinIO с указанными UUID.
     * Вызывается по расписанию, заданному в свойстве application.yml "job.schedule.cron".
     */
    @Scheduled(cron = "${job.schedule.cron_delete_old_files}")
    public void cleanFileStorage() {
        log.info("Получение списка UUID из FilePool");
        List<UUID> uuidList = filePoolFeignClient.getListOfOldRequestFile().getBody();
        if (CollectionUtils.isEmpty(uuidList)) {
            log.warn("Полученная выборка из FilePool пуста - старые файлы из MinIO удалены ранее");
        } else {
            log.info("Данные из FilePool получены");
            log.info("Удаление старых файлов из FileStorage");
            try {
                fileFeignClient.deleteOldFiles(uuidList);
                log.info("Удаление старых файлов из FileStorage выполнено успешно");
            } catch (Exception e) {
                log.warn("Данные из FileStorage удалить не удалось");
                throw new RuntimeException(e);
            }
            log.info("Изменение статуса хранения файла");
            try {
                filePoolFeignClient.markFilePoolRemoved(uuidList);
                log.info("Изменение статуса хранения файла выполнено успешно");
            } catch (Exception e) {
                log.warn("Изменение статуса хранения файла завершено с ошибкой");
                throw new RuntimeException(e);
            }
        }
    }
}
