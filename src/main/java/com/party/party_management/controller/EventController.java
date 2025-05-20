package com.party.party_management.controller;

import com.party.party_management.dto.EventResponseDTO;
import com.party.party_management.dto.EventRequestDTO;
import com.party.party_management.dto.EventUpdateRequestDTO;
import com.party.party_management.model.Event;
import com.party.party_management.security.UserDetailsImpl;
import com.party.party_management.service.EventService;
import com.sun.jdi.request.EventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventResponseDTO>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<EventResponseDTO> response = eventService.getAllEvents(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<EventResponseDTO>> getUpcomingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<EventResponseDTO> response = eventService.getUpcomingEvents(page, size);
        return ResponseEntity.ok(response);
    }

    private EventResponseDTO convertToDto(Event event) {
        return new EventResponseDTO(
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
    public ResponseEntity<EventResponseDTO> createEvent(
            @Valid @RequestBody EventRequestDTO request,
            Authentication authentication) {

        // Verifica se o usuário autenticado é o organizador
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(request.getOrganizerId())) {
            throw new AccessDeniedException("Você só pode criar eventos como organizador");
        }

        EventResponseDTO response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Cacheable(value = "events", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        EventResponseDTO response = eventService.getEventById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventUpdateRequestDTO request,
            Authentication authentication) {

        // Verifica autenticação e permissões
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        EventResponseDTO response = eventService.updateEvent(id, request, userDetails.getId());
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