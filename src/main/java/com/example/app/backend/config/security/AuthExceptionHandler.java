package com.example.app.backend.config.security;

import com.example.app.backend.dto.userDtos.ErrorDto;
import com.example.app.backend.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getCode())
                .body(new ErrorDto(ex.getMessage()));
    }

}
