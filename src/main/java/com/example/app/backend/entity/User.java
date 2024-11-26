package com.example.app.backend.entity;

import com.example.app.backend.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "password")
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    private List<Book> publishedBooks;

    public void addPublishedBook(Book book) {
        if (publishedBooks == null) publishedBooks = new ArrayList<>();
        publishedBooks.add(book);
        book.setPublisher(this);
    }

}
