package com.example.app.backend.util;

import com.example.app.backend.dto.userDtos.SignUpDto;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppConvector {

    private final ModelMapper modelMapper;

    @Autowired
    public AppConvector(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertToUser(SignUpDto signUpDto){
        modelMapper.typeMap(SignUpDto.class, User.class).addMappings(
                mapper -> mapper.skip(User::setPassword)
        );
        return modelMapper.map(signUpDto, User.class);
    }
}
