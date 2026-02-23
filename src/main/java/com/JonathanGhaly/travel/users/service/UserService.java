package com.JonathanGhaly.travel.users.service;


import com.JonathanGhaly.travel.users.dto.CreateUserRequestDto;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    public UserResponseDto create(CreateUserRequestDto request);
    public UserResponseDto getProfile(String keycloakId);
}