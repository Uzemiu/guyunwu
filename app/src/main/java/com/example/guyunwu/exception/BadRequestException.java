package com.example.guyunwu.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
