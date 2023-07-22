package org.example.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DepartmentDto;
import org.example.service.DepartmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//
@Component
@Slf4j
@RequiredArgsConstructor
public class DepartmentListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DepartmentService departmentService;

    @RabbitListener(queues = "department")
    public void receiveDepartment(String departmentJson) {
        try {
            // Преобразование JSON в объект DepartmentDto
            log.info("Получение Департамента из очереди");
            DepartmentDto departmentDto = objectMapper.readValue(departmentJson, DepartmentDto.class);
            log.info("Департамент успешно получен из очереди");
            departmentService.saveDepartment(departmentDto);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }
    }
}
