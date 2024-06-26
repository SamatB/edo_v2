package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.feign.EmployeeFeignClient;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeFeignClient employeeFeignClient;
    /**
     * сервис получения emails от service по ids
     * @param ids Коллекция id Employee.
     * @return Collection<String>  Коллекция emails Employee.
     */
    @Override
    public Collection<String> getEmailsByIds(Collection<Long> ids) {
        if(ids == null) {
            throw new IllegalArgumentException("Коллекция id Employee не может быть null");
        }
        if (ids.isEmpty()) {
            return List.of();
        }
        log.info("Коллекция emails успешно получен из очереди");
        return employeeFeignClient.getEmailsByIds(ids);
    }
}

