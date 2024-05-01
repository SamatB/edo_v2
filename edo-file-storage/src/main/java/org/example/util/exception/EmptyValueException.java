package org.example.util.exception;

public class EmptyValueException extends RuntimeException {

    public EmptyValueException() {
        super();
    }

    public EmptyValueException(String message) {
        super(message);
    }

}
