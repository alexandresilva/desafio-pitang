package com.party.party_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long organizerId;
    private String organizerName;
    private boolean isPast;
    private boolean isOngoing;

    public EventResponseDTO(Long id, String title, String description, String location,
                            LocalDateTime startDate, LocalDateTime endDate,
                            Long organizerId, String organizerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.isPast = endDate != null && endDate.isBefore(LocalDateTime.now());
        this.isOngoing = startDate.isBefore(LocalDateTime.now()) &&
                (endDate == null || endDate.isAfter(LocalDateTime.now()));
    }

    // equals, hashCode e toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResponseDTO that = (EventResponseDTO) o;
        return isPast == that.isPast &&
                isOngoing == that.isOngoing &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(organizerId, that.organizerId) &&
                Objects.equals(organizerName, that.organizerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, location, startDate, endDate,
                organizerId, organizerName, isPast, isOngoing);
    }

    @Override
    public String toString() {
        return "EventResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", organizerId=" + organizerId +
                ", organizerName='" + organizerName + '\'' +
                ", isPast=" + isPast +
                ", isOngoing=" + isOngoing +
                '}';
    }
}