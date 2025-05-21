package com.party.party_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}