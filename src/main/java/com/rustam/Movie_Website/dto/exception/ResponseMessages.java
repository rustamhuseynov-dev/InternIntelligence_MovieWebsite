package com.rustam.Movie_Website.dto.exception;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {

    String key();
    String message();
    HttpStatus status();
}
