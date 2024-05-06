package org.example.util.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;

}
