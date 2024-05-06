package org.example.util.exception.handler;

import org.example.util.exception.EmptyValueException;
import org.example.util.exception.exception_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Данный класс перехватытвает и обрабатывает классы-exception, которые вызваны в его теле
 * с аннотацией @ExceptionHandler
 */
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmptyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse emptyValueHandle(EmptyValueException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getName(), e.getMessage());
    }

}