package org.example.utils.exception.handler;

import org.example.utils.exception.EmptyValueException;
import org.example.utils.exception.exception_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmptyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse emptyValueHandle(EmptyValueException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getName(), e.getMessage());
    }

}