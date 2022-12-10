package com.example.guyunwu.exception;

import java.io.IOException;

public class BadRequestException extends IOException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
