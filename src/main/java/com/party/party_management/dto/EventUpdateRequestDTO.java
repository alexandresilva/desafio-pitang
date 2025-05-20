package com.party.party_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateRequestDTO {
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        private String title;

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        private String description;

        private String location;

        @Future(message = "Data de início deve ser no futuro")
        private LocalDateTime startDate;

        @Future(message = "Data de término deve ser no futuro")
        private LocalDateTime endDate;
}