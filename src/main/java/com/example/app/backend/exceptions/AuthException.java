package com.example.app.backend.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException{
    private final HttpStatus code;

    public AuthException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

}
