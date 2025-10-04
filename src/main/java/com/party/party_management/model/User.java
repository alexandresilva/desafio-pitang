package com.party.party_management.model;

import java.time.Instant;

import com.party.party_management.enumerate.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING) // Garante que o valor da enum seja armazenado como String (ONLINE, OFFLINE) no DB
    private UserStatus status;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    private String tags;

    // ✅ Construtor padrão (obrigatório para JPA)
    public User() {
        // Inicializa o status para OFFLINE por padrão
        this.status = UserStatus.OFFLINE; 
    }

    // ✅ Construtor com campos básicos
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ✅ Construtor para signup (usado no AuthController)
    public User(String username, String email, String fullName, String password, String roleString) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.status = UserStatus.OFFLINE; // Novo usuário começa offline
        // Nota: roleString não é usado aqui pois role é uma entidade separada
        // O AuthController precisará ser ajustado para definir a role corretamente
    }

    // Getters e setters (mantidos iguais)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public UserStatus getStatus() {
    	return status;
    }
    
    public void setStatus(UserStatus status) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}