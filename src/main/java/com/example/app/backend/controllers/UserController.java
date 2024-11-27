package com.example.app.backend.controllers;

import com.example.app.backend.dto.userDtos.SignUpDto;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"http://localhost:5173/"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Validated SignUpDto signUpDto){
        UserDto userDto = userService.register(signUpDto);
        return ResponseEntity.ok(userDto);
    }
}
