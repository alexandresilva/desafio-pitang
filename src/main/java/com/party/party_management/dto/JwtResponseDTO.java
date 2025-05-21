package com.party.party_management.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponseDTO {
	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponseDTO(String token, Long id, String username, String email, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
}