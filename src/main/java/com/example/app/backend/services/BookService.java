package com.example.app.backend.services;

import com.example.app.backend.dto.bookDtos.BookDto;
import com.example.app.backend.dto.bookDtos.CreateBookDto;
import com.example.app.backend.entity.Book;
import com.example.app.backend.entity.User;
import com.example.app.backend.exceptions.AppException;
import com.example.app.backend.repositories.BookRepository;
import com.example.app.backend.repositories.UserRepository;
import com.example.app.backend.util.AppConvector;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AppConvector convector;

    public BookService(BookRepository bookRepository, UserRepository userRepository, AppConvector convector) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.convector = convector;
    }

    @Transactional(readOnly = true)
    public List<BookDto> findAll(){
        return bookRepository.findAll().stream()
                .map(convector::convertToBookDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDto findById(long id){
        return convector.convertToBookDto(bookRepository.findById(id).orElseThrow(
                () -> new AppException("There is no book with this id", HttpStatus.NOT_FOUND)
        ));
    }

    @Transactional
    public BookDto save(CreateBookDto createBookDto) {
        Optional<Book> book = bookRepository.findBookByTitle(createBookDto.getTitle());

        if (book.isPresent()) {
            throw new AppException("Book already exists", HttpStatus.BAD_REQUEST);
        }

        User publisher = userRepository.findById(createBookDto.getPublisherId()).orElse(null);

        Book bookToSave = convector.convertToBook(createBookDto);
        if (publisher != null)
            publisher.addPublishedBook(bookToSave);

        Book savedBook = bookRepository.save(bookToSave);
        return convector.convertToBookDto(savedBook);
    }

    @Transactional
    public boolean delete(Long bookId) {
        Book bookToDelete = bookRepository.findById(bookId).orElse(null);
        if (bookToDelete != null)
            bookRepository.deleteById(bookId);
        return bookToDelete != null;
    }
}
