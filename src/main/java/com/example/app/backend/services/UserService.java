package com.example.app.backend.services;

import com.example.app.backend.dto.userDtos.CredentialsDto;
import com.example.app.backend.dto.userDtos.SignUpDto;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.entity.User;
import com.example.app.backend.enums.Role;
import com.example.app.backend.exceptions.AppException;
import com.example.app.backend.repositories.UserRepository;
import com.example.app.backend.util.AppConvector;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppConvector convector;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AppConvector convector) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.convector = convector;
    }

    @Transactional(readOnly = true)
    public UserDto login(CredentialsDto credentialsDto){
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(
                CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword()
        )){
            return convector.convertToUserDto(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public UserDto register(SignUpDto signUpDto){
        Optional<User> optionalUser = userRepository.findByEmail(signUpDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new AppException("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = convector.convertToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        return convector.convertToUserDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto findUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return convector.convertToUserDto(user);
    }
}
