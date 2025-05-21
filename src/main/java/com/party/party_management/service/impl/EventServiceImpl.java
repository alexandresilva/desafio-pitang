package com.party.party_management.service.impl;

import com.party.party_management.dto.EventRequestDTO;
import com.party.party_management.dto.EventResponseDTO;
import com.party.party_management.dto.EventUpdateRequestDTO;
import com.party.party_management.exception.ResourceNotFoundException;
import com.party.party_management.model.Event;
import com.party.party_management.model.User;
import com.party.party_management.repository.EventRepository;
import com.party.party_management.repository.UserRepository;
import com.party.party_management.service.EventService;
import com.party.party_management.service.mock.MockNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(MockNotificationService.class);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Event> findAllEvents(int page, int size) {
        return null;
    }

    @Override
    public Page<EventResponseDTO> getAllEvents(int page, int size) {
        Page<Event> events = eventRepository.findAll(
                PageRequest.of(page, size, Sort.by("startDateTime").ascending())
        );
        return events.map(this::convertToDto);
    }

    @Override
    public Page<EventResponseDTO> getUpcomingEvents(int page, int size) {
        return null;
    }

    @Override
    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO request) {
        if (request.getEndDateTime() != null &&
                request.getEndDateTime().isBefore(request.getEndDateTime())) {
            throw new IllegalArgumentException("Data de término deve ser após a data de início");
        }

        User organizer = userRepository.findById(request.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizador não encontrado"));

        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setStartDate(request.getStartDateTime());
        event.setEndDate(request.getEndDateTime());
        event.setOrganizer(organizer);
        event.setCreatedAt(LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        return convertToDto(savedEvent);
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

    public EventResponseDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento com id [" + id +"] não encontrado"));

        return convertToDto(event);
    }

    @Override
    @Transactional
    public EventResponseDTO updateEvent(Long eventId, EventUpdateRequestDTO request, Long organizerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new AccessDeniedException("Apenas o organizador pode atualizar o evento");
        }

        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getLocation() != null) {
            event.setLocation(request.getLocation());
        }
        if (request.getStartDate() != null) {
            event.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            if (request.getEndDate().isBefore(event.getStartDate())) {
                throw new IllegalArgumentException("Data de término deve ser após a data de início");
            }
            event.setEndDate(request.getEndDate());
        }

        Event updatedEvent = eventRepository.save(event);

       return convertToDto(updatedEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId, Long organizerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com id: " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new AccessDeniedException("Apenas o organizador pode deletar o evento");
        }

        if (event.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Não é possível deletar eventos que já começaram");
        }

        eventRepository.delete(event);

        // 5. Log simples (substitui o auditService)
        log.info("Evento {} deletado pelo usuário {}", eventId, organizerId);
    }

}