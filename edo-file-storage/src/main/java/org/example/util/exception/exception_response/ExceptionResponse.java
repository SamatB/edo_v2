package org.example.util.exception.exception_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Данный класс-exception представляет структуру ответа exception
 */
@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;

}
