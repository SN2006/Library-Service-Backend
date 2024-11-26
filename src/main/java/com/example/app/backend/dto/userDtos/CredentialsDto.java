package com.example.app.backend.dto.userDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CredentialsDto {
    @NotNull
    private String email;
    @NotNull
    private char[] password;
}
