package com.JonathanGhaly.travel.users.mapper;

import com.JonathanGhaly.travel.users.domain.User;
import com.JonathanGhaly.travel.users.dto.CreateUserRequestDto;
import com.JonathanGhaly.travel.users.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "keycloakId", source = "keycloakId")
    User mapRegisterDtoToEntity(CreateUserRequestDto dto, String keycloakId);
}