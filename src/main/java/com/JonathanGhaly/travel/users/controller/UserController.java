package com.JonathanGhaly.travel.users.controller;

import com.JonathanGhaly.travel.users.dto.CreateUserRequestDto;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;
import com.JonathanGhaly.travel.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody CreateUserRequestDto request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public UserResponseDto me(@AuthenticationPrincipal Jwt jwt) {
        return service.getProfile(jwt.getSubject());
    }
}