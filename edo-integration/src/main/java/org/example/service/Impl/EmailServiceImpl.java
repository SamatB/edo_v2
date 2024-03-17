package org.example.service.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    final String email = "edo.2.kate@gmail.com";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /***
     * Методы для отправки почты с помощью Spring Mail API
     * и проверяет на валидность переданных параметров
     * @param to принимает почту, на которое должно прийти сообщение
     * @param subject принимает тему для сообщения
     * @param text принимает текс для сообщения
     * @throws IllegalArgumentException если переданные данные не валидны
     */
    @Override
    public SimpleMailMessage sendEmail(String to, String subject, String text) {
        validParams(to, subject, text);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        emailSender.send(simpleMailMessage);

        return simpleMailMessage;
    }

    /***
     * Валидирует параметры электронного письма перед его отправкой.
     * Проверяет, что переданный адрес электронной почты соответствует формату валидных email-адресов,
     * а также что тема и текст письма не равны null. В случае, если какой-либо из параметров не проходит валидацию,
     * метод логирует соответствующее информационное сообщение и генерирует исключение IllegalArgumentException.
     * @param to
     * @param subject
     * @param text
     * @throws IllegalArgumentException если переданные данные не валидны
     */
    private void validParams(String to, String subject, String text) {
        boolean flag = false;
        if (to == null || !VALID_EMAIL_ADDRESS_REGEX.matcher(to).matches()) {
            log.info("На вход пришел некорректный email: {}", to);
            flag = true;
        }
        if (subject == null) {
            log.info("Тема сообщения не может быть null");
            flag = true;
        }
        if (text == null) {
            log.info("Сообщение не может быть null");
            flag = true;
        }

        if (flag) {
            throw new IllegalArgumentException();
        }
    }

}
