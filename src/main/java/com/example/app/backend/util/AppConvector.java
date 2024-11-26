package com.example.app.backend.util;

import com.example.app.backend.dto.bookDtos.BookDto;
import com.example.app.backend.dto.bookDtos.CreateBookDto;
import com.example.app.backend.dto.userDtos.SignUpDto;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.entity.Book;
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

    public Book convertToBook(CreateBookDto createBookDto) {
        modelMapper.typeMap(CreateBookDto.class, Book.class).addMappings(
                mapper -> mapper.skip(Book::setId)
        );
        return modelMapper.map(createBookDto, Book.class);
    }

    public BookDto convertToBookDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setPublisherId(
                book.getPublisher() != null ? book.getPublisher().getId() : null
        );
        return bookDto;
    }
}
