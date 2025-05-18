package com.party.party_management.dto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        String location,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long organizerId,
        String organizerName,
        boolean isPast,
        boolean isOngoing
) {

    public EventResponse {
        isPast = endDate != null && endDate.isBefore(LocalDateTime.now());
        isOngoing = startDate.isBefore(LocalDateTime.now()) &&
                (endDate == null || endDate.isAfter(LocalDateTime.now()));
    }

    public EventResponse(Long id, String title, String description, String location,
                         LocalDateTime startDate, LocalDateTime endDate,
                         Long organizerId, String organizerName) {
        this(id, title, description, location, startDate, endDate,
                organizerId, organizerName,
                endDate != null && endDate.isBefore(LocalDateTime.now()),
                startDate.isBefore(LocalDateTime.now()) &&
                        (endDate == null || endDate.isAfter(LocalDateTime.now())));
    }
}