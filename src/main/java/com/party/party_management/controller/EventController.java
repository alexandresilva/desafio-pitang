package com.party.party_management.controller;

import com.party.party_management.dto.EventResponse;
import com.party.party_management.dto.EventRequest;
import com.party.party_management.dto.EventUpdateRequest;
import com.party.party_management.model.Event;
import com.party.party_management.security.UserDetailsImpl;
import com.party.party_management.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<EventResponse> response = eventService.getAllEvents(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<EventResponse>> getUpcomingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<EventResponse> response = eventService.getUpcomingEvents(page, size);
        return ResponseEntity.ok(response);
    }

    private EventResponse convertToDto(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartDate(),
                event.getEndDate(),
                event.getOrganizer().getId(),
                event.getOrganizer().getUsername()
        );
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody EventRequest request,
            Authentication authentication) {

        // Verifica se o usuário autenticado é o organizador
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(request.organizerId())) {
            throw new AccessDeniedException("Você só pode criar eventos como organizador");
        }

        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Cacheable(value = "events", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse response = eventService.getEventById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventUpdateRequest request,
            Authentication authentication) {

        // Verifica autenticação e permissões
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        EventResponse response = eventService.updateEvent(id, request, userDetails.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            Authentication authentication) {

        // Obtém o ID do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();

        eventService.deleteEvent(id, currentUserId);
        return ResponseEntity.noContent().build();
    }
}