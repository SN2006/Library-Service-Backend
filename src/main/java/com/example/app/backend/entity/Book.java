package com.example.app.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "title")
    private String title;
    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "page_number")
    private Integer pageNumber;
    @NotNull
    @Column(name = "year")
    private Integer year;
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @ManyToOne
    private User publisher;

}
