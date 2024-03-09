package org.example.utils;

import org.example.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Comparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для ультилиты SortEmployeeByLastName.
 */
@SpringBootTest
class SortEmployeeByLastNameTest {


    @Test
    public void whenComparatorByLastName() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();

        employee1.setFirstName("Иван");
        employee1.setLastName("Иванович");
        employee1.setMiddleName("Петрович");

        employee2.setFirstName("Алена");
        employee2.setLastName("Буквова");
        employee2.setMiddleName("Мирославовна");

        Comparator<Employee> comparator = new SortEmployeeByLastName();
        int rsl = comparator.compare(
                employee1, employee2
        );

        assertThat(rsl).isGreaterThan(0);
    }

}