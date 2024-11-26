package com.example.app.backend.config.security;

import com.example.app.backend.dto.userDtos.ErrorDto;
import com.example.app.backend.exceptions.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AuthException ex) {
        return ResponseEntity.status(ex.getCode())
                .body(new ErrorDto(ex.getMessage()));
    }

}
