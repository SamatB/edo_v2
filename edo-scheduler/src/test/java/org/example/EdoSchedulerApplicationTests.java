package org.example;

import org.example.service.DataFetchingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Тесты для приложения EdoScheduler.
 */
@SpringBootTest
public class EdoSchedulerApplicationTests {
    /**
     * Сервис для извлечения данных.
     */
    @Autowired
    private DataFetchingService dataFetchingService;

    /**
     * Тестирование метода fetchDataAndConvert() класса DataFetchingService.
     * Метод проверяет успешность получения данных из внешнего хранилища
     * и их преобразование в DTO с помощью указанного метода.
     * В случае неуспешного выполнения, генерируется исключение AssertionError.
     */
    @Test
    public void testFetchDataAndConvert() {
        dataFetchingService.fetchDataAndConvert();
        boolean isDataConversionSuccessful = dataFetchingService.isDataConversionSuccessful();
        Assertions.assertTrue(isDataConversionSuccessful, "Получение данных из внешнего хранилища и преобразование их в DTO с помощью метода fetchDataAndConvert() класса DataFetchingService завершено неуспешно!");
    }
}