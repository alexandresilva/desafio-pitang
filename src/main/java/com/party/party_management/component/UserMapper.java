package com.party.party_management.component;

import com.party.party_management.dto.UserResponseDTO;
import com.party.party_management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO toDto(User user);
}
