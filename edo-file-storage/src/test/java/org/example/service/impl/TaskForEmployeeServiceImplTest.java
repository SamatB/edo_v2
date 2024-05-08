package org.example.service.impl;

import org.example.dto.TaskForEmployeeDto;
import org.example.service.FileStorageService;
import org.example.util.exception.EmptyValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование полей класса TaskForEmployeeDto на null.
 */
@SpringBootTest
class TaskForEmployeeServiceImplTest {

    @Mock
    private FileStorageService fileStorageService;

    private TaskForEmployeeServiceImpl taskForEmployeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskForEmployeeService = new TaskForEmployeeServiceImpl(fileStorageService);
    }

    /**
     * Тест ошибки, когда имя создающего задание не дано.
     */
    @Test
    void emptyTaskCreatorFirstName_ShouldThrowException() {
        TaskForEmployeeDto taskForEmployeeDto = new TaskForEmployeeDto();
        taskForEmployeeDto.setTaskCreatorFirstName("");
        taskForEmployeeDto.setTaskCreatorLastName("Wood");

        assertThrows(EmptyValueException.class, () -> taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto));
    }

    /**
     * Тест ошибки, когда фамилия создающего задание не дана.
     */
    @Test
    void emptyTaskCreatorLastName_ShouldThrowException() {
        TaskForEmployeeDto taskForEmployeeDto = new TaskForEmployeeDto();
        taskForEmployeeDto.setTaskCreatorFirstName("John");
        taskForEmployeeDto.setTaskCreatorLastName("");

        assertThrows(EmptyValueException.class, () -> taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto));
    }

    /**
     * Тест ошибки, когда имя исполнителя задания не дано.
     */
    @Test
    void emptyExecutorFirstName_ShouldThrowException() {
        TaskForEmployeeDto taskForEmployeeDto = new TaskForEmployeeDto();
        taskForEmployeeDto.setTaskCreatorFirstName("John");
        taskForEmployeeDto.setTaskCreatorLastName("Wood");
        taskForEmployeeDto.setExecutorFirstName("");
        taskForEmployeeDto.setExecutorLastName("Price");

        assertThrows(EmptyValueException.class, () -> taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto));
    }

    /**
     * Тест ошибки, когда фамилия исполнителя задания не дана.
     */
    @Test
    void emptyExecutorLastName_ShouldThrowException() {
        TaskForEmployeeDto taskForEmployeeDto = new TaskForEmployeeDto();
        taskForEmployeeDto.setTaskCreatorFirstName("John");
        taskForEmployeeDto.setTaskCreatorLastName("Wood");
        taskForEmployeeDto.setExecutorFirstName("Alex");
        taskForEmployeeDto.setExecutorLastName("");

        assertThrows(EmptyValueException.class, () -> taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto));
    }

    /**
     * Тест ошибки, когда UUID не дано.
     */
    @Test
    void emptyUuid_ShouldThrowException() {
        TaskForEmployeeDto taskForEmployeeDto = new TaskForEmployeeDto();
        taskForEmployeeDto.setTaskCreatorFirstName("John");
        taskForEmployeeDto.setTaskCreatorLastName("Wood");
        taskForEmployeeDto.setExecutorFirstName("Alex");
        taskForEmployeeDto.setExecutorLastName("Price");
        taskForEmployeeDto.setUuid("");

        assertThrows(EmptyValueException.class, () -> taskForEmployeeService.generateTaskForEmployeeIntoPDF(taskForEmployeeDto));
    }
}