package org.example.utils;

import org.example.entity.Employee;

import java.util.Comparator;


/**
 *  Утилитный класс для Employee
 *  помогает отсортировать список Employee по фамилии
 */
public class SortEmployeeByLastName implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getLastName().compareTo(o2.getLastName());
    }
}
