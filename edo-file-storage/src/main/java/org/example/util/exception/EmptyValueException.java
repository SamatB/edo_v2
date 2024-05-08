package org.example.util.exception;

/**
 * Класс EmptyValueException? вывызвается в местах, где поля не должны быть null
 */
public class EmptyValueException extends RuntimeException {

    public EmptyValueException(String message) {
        super(message);
    }
}
