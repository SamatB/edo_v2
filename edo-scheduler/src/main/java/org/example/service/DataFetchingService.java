package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.dto.EmployeeDto;
import org.example.utils.ExternalData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

/**
 * Сервис для получения данных и преобразования их в объекты DTO.
 */
@Service
public class DataFetchingService {
    /**
     * Клиент RestTemplate для выполнения HTTP-запросов к внешнему API.
     */
    private final RestTemplate restTemplate;
    /**
     * Объект ObjectMapper для преобразования JSON-строк в объекты Java и наоборот.
     */
    private final ObjectMapper objectMapper;
    /**
     * Объект Logger для логирования информации.
     */
    private final Logger logger;
    @Value("${external.storage.url}")
    private String externalApiUrl;

    /**
     * Конструктор класса DataFetchingService.
     */
    public DataFetchingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.logger = LoggerFactory.getLogger(DataFetchingService.class);
    }

    /**
     * Метод для получения данных и преобразования их в объекты DTO.
     * Вызывается по расписанию, заданному в свойстве application.yml "job.schedule.cron".
     */
    @Scheduled(cron = "${job.schedule.cron}")
    public void fetchDataAndTransform() {
        String externalApiUrl = "http://xn--d1ab2a.space/mock/employees";
        ResponseEntity<String> response = restTemplate.exchange(externalApiUrl, HttpMethod.GET, null, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String json = response.getBody();
            try {
                // Преобразование данных из внешнего хранилища в список ExternalData
                List<ExternalData> externalData = mapToExternalData(json);
                // Преобразование списка объектов ExternalData в список объектов DTO
                List<EmployeeDto> employeeDto = mapToEmployeeDto(externalData);
                List<DepartmentDto> departmentDto = mapToDepartmentDto(externalData);
                logger.info("Данные из внешнего хранилища успешно получены и преобразованы в DTO.");
            } catch (Exception e) {
                // Обработка ошибки преобразования данных
                logger.error("Ошибка преобразования данных.");
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки получения данных
            logger.warn("Ошибка получения данных.");
        }
    }

    /**
     * Метод для преобразования JSON в список объектов ExternalData.
     *
     * @param json JSON-строка, содержащая данные из внешнего хранилища.
     * @return Список объектов ExternalData.
     * @throws Exception В случае ошибки при преобразовании JSON.
     */
    private List<ExternalData> mapToExternalData(String json) throws Exception {
        // Используется jackson ObjectMapper для маппинга JSON в объекты ExternalData
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, new TypeReference<List<ExternalData>>() {
        });
    }

    /**
     * Метод для преобразования списка объектов ExternalData в список объектов EmployeeDto.
     *
     * @param externalDataList Список объектов ExternalData.
     * @return Список объектов EmployeeDto.
     */
    private List<EmployeeDto> mapToEmployeeDto(List<ExternalData> externalDataList) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<AddressDto> addressDtoList = new ArrayList<>();
        Set<String> uniqueIds = new HashSet<>();
        for (ExternalData externalData : externalDataList) {
            String externalId = externalData.getId().toString();
            if (uniqueIds.contains(externalId)) {
                continue; // Пропускаем, если externalId уже присутствует
            }
            EmployeeDto employeeDto = new EmployeeDto();
            AddressDto addressDto = new AddressDto();
            addressDto.setFullAddress(externalData.getLocation().toString());
            addressDto.setStreet(externalData.getLocation().getStreet().getName());
            addressDto.setHouse(externalData.getLocation().getStreet().getNumber());
            addressDto.setIndex(externalData.getLocation().getPostcode());
            addressDto.setCity(externalData.getLocation().getCity());
            addressDto.setRegion(externalData.getLocation().getState());
            addressDto.setCountry(externalData.getLocation().getCountry());
            addressDtoList.add(addressDto);
            employeeDto.setFirstName(externalData.getName().getFirst());
            employeeDto.setLastName(externalData.getName().getLast());
            employeeDto.setMiddleName(externalData.getName().getMiddle());
            employeeDto.setAddress(externalData.getLocation().toString());
            employeeDto.setAddressDetails(addressDto);
            employeeDto.setPhotoUrl(externalData.getPicture().toString());
            employeeDto.setExternalId(externalData.getId().toString());
            employeeDto.setPhone(externalData.getPhone());
            employeeDto.setWorkPhone(externalData.getCell());
            employeeDto.setBirthDate(LocalDate.parse(externalData.getDob().getDate()));
            employeeDto.setUsername(externalData.getLogin().getUsername());
            employeeDto.setCreationDate(ZonedDateTime.parse(externalData.getRegistered().getDate()));
            employeeDtoList.add(employeeDto);
            uniqueIds.add(externalId);
        }
        return employeeDtoList;
    }

    /**
     * Метод для преобразования списка объектов ExternalData в список объектов DepartmentDto.
     *
     * @param externalDataList Список объектов ExternalData.
     * @return Список объектов DepartmentDto.
     */
    private List<DepartmentDto> mapToDepartmentDto(List<ExternalData> externalDataList) {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        Set<String> uniqueIds = new HashSet<>();
        for (ExternalData externalData : externalDataList) {
            String externalId = externalData.getCompany().getId().toString();
            if (uniqueIds.contains(externalId)) {
                continue; // Пропускаем, если externalId уже присутствует
            }
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setExternalId(externalData.getCompany().getId().toString());
            departmentDto.setFullName(externalData.getCompany().getName());
            departmentDto.setAddress((externalData.getCompany().getLocation().toString()));
            departmentDtoList.add(departmentDto);
            uniqueIds.add(externalId);
        }
        return departmentDtoList;
    }
}