package com.swapit.swapitspringboot.exception;

public class DuplicateSkillException extends RuntimeException {
    public DuplicateSkillException(String message) {
        super(message);
    }
}