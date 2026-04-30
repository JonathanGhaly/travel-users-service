package com.JonathanGhaly.travel.users.service.impl;

import com.JonathanGhaly.travel.users.config.KeycloakProperties;
import com.JonathanGhaly.travel.users.domain.User;
import com.JonathanGhaly.travel.users.dto.CreateUserRequestDto;
import com.JonathanGhaly.travel.users.dto.UserEventDto;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;
import com.JonathanGhaly.travel.users.exception.ApiException;
import com.JonathanGhaly.travel.users.kafka.UserEventPublisher;
import com.JonathanGhaly.travel.users.mapper.UserMapper;
import com.JonathanGhaly.travel.users.repository.UserRepository;
import com.JonathanGhaly.travel.users.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final Keycloak keycloak;
    private final KeycloakProperties properties;
    private final UserEventPublisher eventPublisher; // Injected Kafka Publisher

    @Override
    @Transactional
    public UserResponseDto create(CreateUserRequestDto request) {

        // 1️⃣ Create user in Keycloak
        UsersResource usersResource = keycloak.realm(properties.getRealm()).users();

        UserRepresentation userRep = new UserRepresentation();
        // Note: Using record accessors (request.username()) instead of getters (request.getUsername())
        userRep.setUsername(request.username());
        userRep.setEmail(request.email());
        userRep.setFirstName(request.firstName());
        userRep.setLastName(request.lastName());
        userRep.setEnabled(true);

        Response response = usersResource.create(userRep);

        if (response.getStatus() != 201) {
            throw new ApiException("Failed to create user in Keycloak: HTTP " + response.getStatus(),
                    HttpStatus.BAD_REQUEST);
        }

        String keycloakId = CreatedResponseUtil.getCreatedId(response);

        // 2️⃣ Set password in Keycloak
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);

        try {
            usersResource.get(keycloakId).resetPassword(credential);
        } catch (Exception ex) {
            // Rollback Keycloak user if password setting fails
            usersResource.get(keycloakId).remove();
            throw new ApiException("Failed to set password in Keycloak", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 3️⃣ Save user locally
        User user = mapper.mapRegisterDtoToEntity(request, keycloakId);
        User savedUser = repository.save(user);

        // 4️⃣ Publish Event to Kafka
        eventPublisher.publish(new UserEventDto(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                "REGISTERED",
                Instant.now()
        ));

        return mapper.toResponse(savedUser);
    }

    @Override
    @Cacheable(value = "user-profiles", key = "#keycloakId") // Cache Profile lookups via Redis
    public UserResponseDto getProfile(String keycloakId) {
        return repository.findByKeycloakId(keycloakId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }
}