package org.example.feign;

import org.example.dto.TaskForEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Клиент для работы с сервисом edo-file-storage.
 */
@FeignClient(name = "edo-file-storage")
public interface TaskForEmployeeFeignClient {

    /**
     * Данный метод принимает на вход TaskForEmployeeDto и обрабатывается единственным методом TaskForEmployeeFeignClient,
     * в теле ответа отправляется созданный PDF файл формата А4
     *
     * @param taskForEmployeeDto - запрос от клиента.
     * @return - ответ, который содержит созданный PDF файла в виде ByteArrayResource в обертке ResponseEntity,
     * созданный файл открывестя в браузере.
     */
    @PostMapping(value = "/task-for-employee", produces = MediaType.APPLICATION_PDF_VALUE)
    ByteArrayResource convertTaskForEmployeeIntoPDF(@RequestBody TaskForEmployeeDto taskForEmployeeDto);

}
