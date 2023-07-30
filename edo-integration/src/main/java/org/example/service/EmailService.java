package org.example.service;

import org.springframework.stereotype.Service;

/** Сервис отправки почты
*/
@Service
public interface EmailService {
    /** метод отправки почты
     */
    void sendEmail(String to);
}
