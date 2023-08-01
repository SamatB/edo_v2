package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.feign.EmployeeFeignClient;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeFeignClient employeeFeignClient;

    @Override
    public Collection<String> getEmailById(Collection<Long> ids) {
        log.info("Коллекция emails успешно получен из очереди");
        return employeeFeignClient.getByEmails(ids);
    }
}

