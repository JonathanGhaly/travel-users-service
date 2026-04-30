package com.JonathanGhaly.travel.users.dto;

import java.time.Instant;
import java.util.UUID;

public record UserEventDto(
        UUID userId,
        String email,
        String username,
        String status, // e.g., "REGISTERED"
        Instant timestamp
) {}