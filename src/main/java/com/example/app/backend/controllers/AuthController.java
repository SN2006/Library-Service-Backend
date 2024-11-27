package com.example.app.backend.controllers;

import com.example.app.backend.config.security.UserAuthProvider;
import com.example.app.backend.dto.userDtos.CredentialsDto;
import com.example.app.backend.dto.userDtos.SignUpDto;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:5173/"})
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @Autowired
    public AuthController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Validated CredentialsDto credentialsDto){
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Validated SignUpDto signUpDto){
        UserDto userDto = userService.register(signUpDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/api/v1/users/" + userDto.getId()))
                .body(userDto);
    }

}
