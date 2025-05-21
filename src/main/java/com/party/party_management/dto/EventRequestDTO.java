package com.party.party_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class EventRequestDTO {

	@NotBlank(message = "Título é obrigatório")
	@Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
	private String title;

	@Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
	private String description;

	@NotBlank(message = "Localização é obrigatória")
	private String location;

	@Future(message = "Data de início deve ser no futuro")
	@NotNull(message = "Data de início é obrigatória")
	private LocalDateTime startDateTime;

	@Future(message = "Data de término deve ser no futuro")
	private LocalDateTime endDateTime;

	@NotNull(message = "ID do organizador é obrigatório")
	private Long organizerId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Long getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Long organizerId) {
		this.organizerId = organizerId;
	}
}
