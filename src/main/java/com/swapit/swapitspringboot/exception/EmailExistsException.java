package com.swapit.swapitspringboot.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}