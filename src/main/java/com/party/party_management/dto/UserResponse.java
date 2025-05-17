package com.party.party_management.dto;

import com.party.party_management.model.Role;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private Instant createdAt;

    // Construtor
    public UserResponse(Long id, String username, String email, String fullName, String role, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UserResponse(Long id, String username, String email, Set<Role> role) {
    }
}
