package org.example.testcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmployeeDto;
import org.example.service.impl.EmployeeServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.example.configuration.RabbitConfiguration.SAVE_EMPLOYEE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestControllerSendRoRabbitListener {

    private final EmployeeServiceImpl employeeService;
    private final RabbitTemplate rabbitTemplate;
    @PostMapping("/employee")
    public ResponseEntity<String> tryToSaveEmployee(@RequestBody EmployeeDto employeeDto) {
        rabbitTemplate.convertAndSend(SAVE_EMPLOYEE, employeeDto);
        log.info("В очередь был отправлен!");
        return new ResponseEntity<>("Employee saved", HttpStatus.OK);
    }
}
