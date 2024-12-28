package com.rustam.Movie_Website.exception.custom;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final String message;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }
}
