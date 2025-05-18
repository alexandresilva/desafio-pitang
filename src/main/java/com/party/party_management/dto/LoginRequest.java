package com.party.party_management.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username ou email é obrigatório")
        String usernameOrEmail,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {}