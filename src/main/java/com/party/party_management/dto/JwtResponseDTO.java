package com.party.party_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
}