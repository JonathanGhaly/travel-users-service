package com.JonathanGhaly.travel.users.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class UserResponseDto {
    UUID id;
    String username;
    String email;
    String firstName;
    String lastName;
    Instant createdAt;
}
