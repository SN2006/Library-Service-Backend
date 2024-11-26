package com.example.app.backend.controllers;

import com.example.app.backend.dto.bookDtos.BookDto;
import com.example.app.backend.dto.bookDtos.CreateBookDto;
import com.example.app.backend.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookDto>> books() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> bookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody @Valid CreateBookDto createBookDto) {
        return ResponseEntity.ok(bookService.save(createBookDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return bookService.delete(id) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}
