package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.dto.AddressDto;
import org.example.dto.DepartmentDto;
import org.example.dto.EmployeeDto;
import org.example.dto.ExternalDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * URL адрес внешнего хранилища данных.
     */
    private final String externalStorageUrl;
    /**
     * Флаг успешного получения данных из внешнего хранилища и преобразования их в DTO.
     */
    private boolean fetchDataAndConvertSuccessful = false;

    /**
     * Конструктор класса DataFetchingService.
     */
    public DataFetchingService(@Value("${external.storage.url}") String externalStorageUrl) {
        this.externalStorageUrl = externalStorageUrl;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Метод для получения данных и преобразования их в объекты DTO.
     * Вызывается по расписанию, заданному в свойстве application.yml "job.schedule.cron".
     */
    @Scheduled(cron = "${job.schedule.cron}")
    public void fetchDataAndConvert() {
        try {
            List<ExternalDataDto> externalDataDtoList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(externalStorageUrl, ExternalDataDto[].class)));
            if (!externalDataDtoList.isEmpty()) {
                List<DepartmentDto> departmentDto = mapToDepartmentDto(externalDataDtoList);
                List<EmployeeDto> employeeDto = mapToEmployeeDto(externalDataDtoList);
                log.info("Данные из внешнего хранилища успешно получены и преобразованы в DTO. Всего получено и преобразовано " + employeeDto.size() + " Employee и " + departmentDto.size() + " Department.");
                fetchDataAndConvertSuccessful = true;
            } else {
                log.warn("Данные из внешнего хранилища отсутствуют или пусты!");
            }
        } catch (Exception e) {
            log.error("Ошибка при получении и преобразовании данных из внешнего хранилища:", e);
        }
    }

    /**
     * Метод для преобразования объекта Location в объект AddressDto.
     *
     * @param location Объект Location.
     * @return Объект AddressDto.
     */
    private AddressDto mapToAddressDto(ExternalDataDto.Location location) {
        AddressDto addressDto = new AddressDto();
        addressDto.setFullAddress(location.toString());
        addressDto.setStreet(location.getStreet().getName());
        addressDto.setHouse(location.getStreet().getNumber());
        addressDto.setIndex(location.getPostcode());
        addressDto.setCity(location.getCity());
        addressDto.setRegion(location.getState());
        addressDto.setCountry(location.getCountry());
        return addressDto;
    }

    /**
     * Метод для преобразования объекта Company в объект DepartmentDto.
     *
     * @param company Объект Location.
     * @return Объект AddressDto.
     */
    private DepartmentDto mapToDepartmentDto(ExternalDataDto.Company company) {
        AddressDto addressDto = mapToAddressDto(company.getLocation());
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setFullName(company.getName());
        departmentDto.setAddress(addressDto.getFullAddress());
        departmentDto.setAddressDetails(addressDto);
        departmentDto.setExternalId(String.valueOf(company.getId()));
        return departmentDto;
    }

    /**
     * Метод для преобразования списка объектов ExternalData в список объектов EmployeeDto.
     *
     * @param externalDataDtoList Список объектов ExternalData.
     * @return Список объектов EmployeeDto.
     */
    private List<EmployeeDto> mapToEmployeeDto(List<ExternalDataDto> externalDataDtoList) {
        return externalDataDtoList.stream()
                .map(externalDataDto -> {
                    AddressDto addressDto = mapToAddressDto(externalDataDto.getLocation());
                    DepartmentDto departmentDto = mapToDepartmentDto(externalDataDto.getCompany());
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setFirstName(externalDataDto.getName().getFirst());
                    employeeDto.setLastName(externalDataDto.getName().getLast());
                    employeeDto.setMiddleName(externalDataDto.getName().getMiddle());
                    employeeDto.setAddress(addressDto.getFullAddress());
                    employeeDto.setAddressDetails(addressDto);
                    employeeDto.setPhotoUrl(externalDataDto.getPicture().toString());
                    employeeDto.setExternalId(externalDataDto.getId().toString());
                    employeeDto.setPhone(externalDataDto.getPhone());
                    employeeDto.setWorkPhone(externalDataDto.getCell());
                    employeeDto.setBirthDate(LocalDate.parse(externalDataDto.getDob().getDate()));
                    employeeDto.setUsername(externalDataDto.getLogin().getUsername());
                    employeeDto.setCreationDate(ZonedDateTime.parse(externalDataDto.getRegistered().getDate()));
                    employeeDto.setDepartment(departmentDto);
                    return employeeDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Метод для преобразования списка объектов ExternalData в список объектов DepartmentDto.
     *
     * @param externalDataDtoList Список объектов ExternalData.
     * @return Список объектов DepartmentDto.
     */
    private List<DepartmentDto> mapToDepartmentDto(List<ExternalDataDto> externalDataDtoList) {
        return externalDataDtoList.stream()
                .map(externalDataDto -> mapToDepartmentDto(externalDataDto.getCompany()))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Метод, который возвращает boolean флаг успешного преобразования данных.
     *
     * @return boolean флаг успешного преобразования данных.
     */
    public boolean isFetchDataAndConvertSuccessful() {
        return fetchDataAndConvertSuccessful;
    }
}