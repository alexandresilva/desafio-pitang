package com.party.party_management.component;

import com.party.party_management.dto.UserResponseDTO;
import com.party.party_management.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(User user);
}
