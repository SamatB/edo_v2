package org.example.testcontroller;

import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.service.impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestControllerGiveMyEmployee {
    private final EmployeeServiceImpl employeeService;
    @GetMapping("/employee")
    public ResponseEntity<EmployeeDto> giveMeEmployee(@RequestParam Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }
}
