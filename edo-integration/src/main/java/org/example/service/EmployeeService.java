package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
/** Employee сервис
 */
@Service
public interface EmployeeService {
    /** Получение коллекции email по id
     */
    Collection<String> getEmailToId(Collection <Long> ids);
}
