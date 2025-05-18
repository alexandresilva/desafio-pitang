package com.party.party_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventUpdateRequest(
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        String title,

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        String description,

        String location,

        @Future(message = "Data de início deve ser no futuro")
        LocalDateTime startDate,

        @Future(message = "Data de término deve ser no futuro")
        LocalDateTime endDate
) {}
