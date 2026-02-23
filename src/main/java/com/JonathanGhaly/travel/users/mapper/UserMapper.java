package com.JonathanGhaly.travel.users.mapper;


import com.JonathanGhaly.travel.users.domain.User;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto toResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}