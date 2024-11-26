package com.example.app.backend.dto.bookDtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDto {

    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    @Min(1)
    private Integer pageNumber;
    @NotNull
    private Integer year;
    private Long publisherId;

}
