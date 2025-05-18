package com.party.party_management.service.impl;

import com.party.party_management.dto.EventRequest;
import com.party.party_management.dto.EventResponse;
import com.party.party_management.dto.EventUpdateRequest;
import com.party.party_management.exception.ResourceNotFoundException;
import com.party.party_management.model.Event;
import com.party.party_management.model.User;
import com.party.party_management.repository.EventRepository;
import com.party.party_management.repository.UserRepository;
import com.party.party_management.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {
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
    public Page<EventResponse> getAllEvents(int page, int size) {
        Page<Event> events = eventRepository.findAll(
                PageRequest.of(page, size, Sort.by("startDateTime").ascending())
        );
        return events.map(this::convertToDto);
    }

    @Override
    public Page<EventResponse> getUpcomingEvents(int page, int size) {
        return null;
    }

    @Override
    @Transactional
    public EventResponse createEvent(EventRequest request) {
        if (request.endDateTime() != null &&
                request.endDateTime().isBefore(request.startDateTime())) {
            throw new IllegalArgumentException("Data de término deve ser após a data de início");
        }

        User organizer = userRepository.findById(request.organizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizador não encontrado"));

        Event event = new Event();
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setStartDate(request.startDateTime());
        event.setEndDate(request.endDateTime());
        event.setOrganizer(organizer);
        event.setCreatedAt(LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        return convertToDto(savedEvent);
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
                event.getOrganizer().getUsername(),
                event.getEndDate() != null && event.getEndDate().isBefore(LocalDateTime.now()),
                event.getStartDate().isBefore(LocalDateTime.now()) &&
                        (event.getEndDate() == null || event.getEndDate().isAfter(LocalDateTime.now()))
        );
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento com id [" + id +"] não encontrado"));

        return convertToDto(event);
    }

    @Override
    @Transactional
    public EventResponse updateEvent(Long eventId, EventUpdateRequest request, Long organizerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new AccessDeniedException("Apenas o organizador pode atualizar o evento");
        }

        if (request.title() != null) {
            event.setTitle(request.title());
        }
        if (request.description() != null) {
            event.setDescription(request.description());
        }
        if (request.location() != null) {
            event.setLocation(request.location());
        }
        if (request.startDate() != null) {
            event.setStartDate(request.startDate());
        }
        if (request.endDate() != null) {
            if (request.endDate().isBefore(event.getStartDate())) {
                throw new IllegalArgumentException("Data de término deve ser após a data de início");
            }
            event.setEndDate(request.endDate());
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