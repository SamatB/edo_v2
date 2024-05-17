package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.dto.TaskForEmployeeDto;
import org.example.feign.FacsimileFeignClient;
import org.example.feign.TaskForEmployeeFeignClient;
import org.example.service.KeycloakService;
import org.example.service.TaskForEmployeeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskForEmployeeServiceImpl implements TaskForEmployeeService {

    private final TaskForEmployeeFeignClient taskForEmployeeFeignClient;
    private final FacsimileFeignClient facsimileFeignClient;
    private final KeycloakService keycloakService;

    /**
     * Данный метод принимает на вход TaskForEmployeeDto и обрабатывается единственным методом TaskForEmployeeFeignClient, где формируется PDF файл,
     * и методом FacsimileFeignClient по получению UUID факсимиле файла из БД, UUID передается методу TaskForEmployeeFeignClient для загрузки факсимиле-изображения
     * из MinIO.
     * В поля "Ф.И.О создающего задание" и "контактные данные" передаются данные текущего пользователя из метода getEmployeeFromSessionUsername()
     * В теле ответа отправляется созданный PDF файл формата А4
     *
     * @param taskForEmployeeDto - запрос, который нужно обратотать.
     * @return - ответ, который содержит созданный PDF файл в виде ByteArrayResource в обертке ResponseEntity,
     * созданный файл открывестя в браузере.
     */
    @Override
    public  ByteArrayResource convertTaskForEmployeeIntoPDF(TaskForEmployeeDto taskForEmployeeDto) {
        EmployeeDto employeeDto = keycloakService.getEmployeeFromSessionUsername();
        log.info("employeeDto: {}", employeeDto);
        taskForEmployeeDto.setTaskCreatorFirstName(employeeDto.getFirstName());
        taskForEmployeeDto.setTaskCreatorLastName(employeeDto.getLastName());
        taskForEmployeeDto.setTaskCreatorMiddleName(employeeDto.getMiddleName());
        taskForEmployeeDto.setTaskCreatorEmail(employeeDto.getEmail());
        taskForEmployeeDto.setTaskCreatorPhoneNumber(employeeDto.getPhone());
        String uuid = String.valueOf(facsimileFeignClient.getUUIDFacsimileByUserId(employeeDto.getId()));
        taskForEmployeeDto.setUuid(uuid);
        return taskForEmployeeFeignClient.convertTaskForEmployeeIntoPDF(taskForEmployeeDto);
    }
}
