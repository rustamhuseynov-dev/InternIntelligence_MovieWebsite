package com.rustam.Movie_Website.exception.custom;

import lombok.Getter;

@Getter
public class InvalidUUIDFormatException extends RuntimeException {

    private final String message;

    public InvalidUUIDFormatException(String message, IllegalArgumentException e) {
        super(message,e);
        this.message = message;
    }
}
