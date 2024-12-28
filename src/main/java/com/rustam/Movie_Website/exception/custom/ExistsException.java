package com.rustam.Movie_Website.exception.custom;

import lombok.Getter;

@Getter
public class ExistsException extends RuntimeException {

    private final String message;

    public ExistsException(String message) {
        super(message);
        this.message = message;
    }
}
