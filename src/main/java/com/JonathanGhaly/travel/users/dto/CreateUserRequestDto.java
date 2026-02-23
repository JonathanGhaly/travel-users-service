package com.JonathanGhaly.travel.users.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequestDto {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private String firstName;
    private String lastName;

    private Set<String> roles;
}
