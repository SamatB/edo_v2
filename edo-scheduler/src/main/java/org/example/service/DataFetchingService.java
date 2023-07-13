package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.dto.EmployeeDto;
import org.example.utils.ExternalData;
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

/**
 * Сервис для получения данных и преобразования их в объекты DTO.
 */
@Log4j2
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
     * URL адрес внешнего хранилища данных.
     */
    private final String externalStorageUrl;
    /**
     * Флаг успешного получения данныих из внешнего хранилища и преобразования их в DTO.
     */
    private boolean dataConversionSuccessful = false;

    /**
     * Конструктор класса DataFetchingService.
     */
    public DataFetchingService(@Value("${external.storage.url}") String externalStorageUrl) {
        this.externalStorageUrl = externalStorageUrl;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Метод для получения данных и преобразования их в объекты DTO.
     * Вызывается по расписанию, заданному в свойстве application.yml "job.schedule.cron".
     */
    @Scheduled(cron = "${job.schedule.cron}")
    public void fetchDataAndConvert() {
        ResponseEntity<String> response = restTemplate.exchange(externalStorageUrl, HttpMethod.GET, null, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String json = response.getBody();
            try {
                // Преобразование данных из внешнего хранилища в список ExternalData
                List<ExternalData> externalData = mapToExternalData(json);
                // Преобразование списка объектов ExternalData в список объектов DTO
                List<EmployeeDto> employeeDto = mapToEmployeeDto(externalData);
                List<DepartmentDto> departmentDto = mapToDepartmentDto(externalData);
                log.info("Данные из внешнего хранилища успешно получены и преобразованы в DTO.");
                dataConversionSuccessful = true; // Установка флага успешного преобразования данных
            } catch (Exception e) {
                // Обработка ошибки преобразования данных
                log.error("Ошибка преобразования данных.");
                e.printStackTrace();
            }
        } else {
            // Обработка ошибки получения данных
            log.warn("Ошибка получения данных.");
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
        for (ExternalData externalData : externalDataList) {
            ExternalData.Location location = externalData.getLocation();
            ExternalData.Name name = externalData.getName();
            String externalId = String.valueOf(externalData.getId());
            AddressDto addressDto = new AddressDto();
            addressDto.setFullAddress(location.toString());
            addressDto.setStreet(location.getStreet().getName());
            addressDto.setHouse(location.getStreet().getNumber());
            addressDto.setIndex(location.getPostcode());
            addressDto.setCity(location.getCity());
            addressDto.setRegion(location.getState());
            addressDto.setCountry(location.getCountry());
            EmployeeDto employeeDto = new EmployeeDto();
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
            employeeDtoList.add(employeeDto);
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
        Set<DepartmentDto> departmentDtoSet = new HashSet<>();
        for (ExternalData externalData : externalDataList) {
            ExternalData.Company company = externalData.getCompany();
            String externalId = String.valueOf(company.getId());
            AddressDto addressDto = new AddressDto();
            addressDto.setFullAddress(company.getLocation().toString());
            addressDto.setStreet(company.getLocation().getStreet().getName());
            addressDto.setHouse(company.getLocation().getStreet().getNumber());
            addressDto.setIndex(company.getLocation().getPostcode());
            addressDto.setCity(company.getLocation().getCity());
            addressDto.setRegion(company.getLocation().getState());
            addressDto.setCountry(company.getLocation().getCountry());
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setExternalId(externalId);
            departmentDto.setFullName(company.getName());
            departmentDto.setAddress(addressDto.getFullAddress());
            departmentDto.setAddressDetails(addressDto);
            departmentDtoSet.add(departmentDto);
        }
        return new ArrayList<>(departmentDtoSet);
    }

    /**
     * Метод, который возвращает boolean флаг успешного преобразования данных.
     *
     * @return boolean флаг успешного преобразования данных.
     */
    public boolean isDataConversionSuccessful() {
        return dataConversionSuccessful;
    }
}