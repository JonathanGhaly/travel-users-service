package com.JonathanGhaly.travel.users.service;


import com.JonathanGhaly.travel.users.dto.CreateUserRequestDto;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;


public interface UserService {
    UserResponseDto create(CreateUserRequestDto request);

    UserResponseDto getProfile(String keycloakId);
}