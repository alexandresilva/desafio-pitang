package com.party.party_management.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events")
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
    private Instant startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    // JSONB type (PostgreSQL específico para armazenar JSON)
    @Column(columnDefinition = "jsonb")
    private String metadata;

    // Getters e Setters
}