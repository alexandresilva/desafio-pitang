package com.party.party_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public record EventRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        String title,

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        String description,

        @NotBlank(message = "Localização é obrigatória")
        String location,

        @Future(message = "Data de início deve ser no futuro")
        @NotNull(message = "Data de início é obrigatória")
        LocalDateTime startDateTime,

        @Future(message = "Data de término deve ser no futuro")
        LocalDateTime endDateTime,

        @NotNull(message = "ID do organizador é obrigatório")
        Long organizerId
) {}