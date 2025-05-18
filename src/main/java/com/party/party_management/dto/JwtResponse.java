package com.party.party_management.dto;

import java.util.List;

public record JwtResponse(
        String token,
        Long id,
        String username,
        String email,
        List<String> roles
) {}