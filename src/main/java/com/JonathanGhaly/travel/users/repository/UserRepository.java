package com.JonathanGhaly.travel.users.repository;

import com.JonathanGhaly.travel.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByKeycloakId(String keycloakId);
}