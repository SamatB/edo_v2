package org.example.listener;

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
    private final DepartmentService departmentService;

    @RabbitListener(queues = "department")
    public void receiveDepartment(DepartmentDto departmentDto) {
        try {
            log.info("Департамент успешно получен из очереди");
            departmentService.saveDepartment(departmentDto);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения из RabbitMQ: " + e.getMessage());
        }

    }
}
