package com.JonathanGhaly.travel.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CreateUserRequestDto(
        @NotBlank String username,
        @Email String email,
        @NotBlank @Size(min = 6) String password,
        String firstName,
        String lastName,
        Set<String> roles
) {}