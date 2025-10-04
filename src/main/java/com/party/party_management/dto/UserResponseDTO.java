package com.party.party_management.dto;

import com.party.party_management.model.Role;

import java.time.Instant;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Role role;
    private Instant createdAt;
    private String status; 


    // Construtor
    public UserResponseDTO(Long id, String username, String email, String fullName, Role role, Instant createdAt, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
        this.status = status;
    }

    public UserResponseDTO(){
    }

    public UserResponseDTO(Long id, String username, String email, String fullName, Role role, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
