package com.party.party_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    // PostgreSQL enum type
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null check (role in ('USER', 'ADMIN', 'ORGANIZER'))")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> role;

    // PostgreSQL specific types
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    // Array type do PostgreSQL
    @Column(columnDefinition = "text[]")
    private String[] tags;

}