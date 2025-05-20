package com.party.party_management.dto;

import com.party.party_management.model.Role;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private Instant createdAt;

    // Construtor
    public UserResponseDTO(Long id, String username, String email, String fullName, String role, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UserResponseDTO(Long id, String username, String email, Set<Role> role) {
    }

    public UserResponseDTO(Long id, String username, String email, String fullName, Set<Role> role) {
    }

    public UserResponseDTO(Long id, String username, String email, String fullName, Set<Role> role, Instant createdAt) {
    }
}
