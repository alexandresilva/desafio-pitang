package com.party.party_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Tipo específico do PostgreSQL para geolocalização
    @Column(columnDefinition = "POINT")
    private String location;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime createdAt;

    // JSONB type (PostgreSQL específico para armazenar JSON)
    @Column(columnDefinition = "jsonb")
    private String metadata;

    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}