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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void fetchDataAndConvert() {
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
        Map<String, EmployeeDto> employeeDtoMap = new HashMap<>();
        Map<String, AddressDto> addressDtoMap = new HashMap<>();
        for (ExternalData externalData : externalDataList) {
            ExternalData.Location location = externalData.getLocation();
            ExternalData.Name name = externalData.getName();
            String externalId = String.valueOf(externalData.getId());
            EmployeeDto employeeDto = employeeDtoMap.get(externalId);
            AddressDto addressDto = addressDtoMap.get(externalId);
            if (addressDto == null) {
                addressDto = new AddressDto();
                addressDto.setFullAddress(location.toString());
                addressDto.setStreet(location.getStreet().getName());
                addressDto.setHouse(location.getStreet().getNumber());
                addressDto.setIndex(location.getPostcode());
                addressDto.setCity(location.getCity());
                addressDto.setRegion(location.getState());
                addressDto.setCountry(location.getCountry());
            }
            if (employeeDto == null) {
                employeeDto = new EmployeeDto();
                employeeDto.setExternalId(externalId);
                employeeDto.setFirstName(name.getFirst());
                employeeDto.setLastName(name.getLast());
                employeeDto.setMiddleName(name.getMiddle());
                employeeDto.setAddress(addressDto.getFullAddress());
                employeeDto.setAddressDetails(addressDto);
                employeeDto.setPhotoUrl(externalData.getPicture().toString());
                employeeDto.setPhone(externalData.getPhone());
                employeeDto.setWorkPhone(externalData.getCell());
                employeeDto.setBirthDate(LocalDate.parse(externalData.getDob().getDate()));
                employeeDto.setUsername(externalData.getLogin().getUsername());
                employeeDto.setCreationDate(ZonedDateTime.parse(externalData.getRegistered().getDate()));
                employeeDtoMap.put(externalId, employeeDto);
            }
        }
        return new ArrayList<>(employeeDtoMap.values());
    }

    /**
     * Метод для преобразования списка объектов ExternalData в список объектов DepartmentDto.
     *
     * @param externalDataList Список объектов ExternalData.
     * @return Список объектов DepartmentDto.
     */
    private List<DepartmentDto> mapToDepartmentDto(List<ExternalData> externalDataList) {
        Map<String, DepartmentDto> departmentDtoMap = new HashMap<>();
        for (ExternalData externalData : externalDataList) {
            ExternalData.Company company = externalData.getCompany();
            String externalId = String.valueOf(company.getId());
            DepartmentDto departmentDto = departmentDtoMap.get(externalId);
            if (departmentDto == null) {
                departmentDto = new DepartmentDto();
                departmentDto.setExternalId(externalId);
                departmentDto.setFullName(company.getName());
                departmentDto.setAddress(company.getLocation().toString());
                departmentDtoMap.put(externalId, departmentDto);
            }
        }
        return new ArrayList<>(departmentDtoMap.values());
    }
}