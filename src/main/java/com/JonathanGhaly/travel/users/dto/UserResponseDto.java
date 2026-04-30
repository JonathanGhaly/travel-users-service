package com.JonathanGhaly.travel.users.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName,
        Instant createdAt
) {}