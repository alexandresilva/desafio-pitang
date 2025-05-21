package com.party.party_management.service;

import com.party.party_management.dto.EventRequestDTO;
import com.party.party_management.dto.EventResponseDTO;
import com.party.party_management.dto.EventUpdateRequestDTO;
import com.party.party_management.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface EventService {
    List<Event> findAllEvents(int page, int size);
    Page<EventResponseDTO> getAllEvents(int page, int size);
    Page<EventResponseDTO> getUpcomingEvents(int page, int size);

    @Transactional
    EventResponseDTO createEvent(EventRequestDTO request);

    EventResponseDTO getEventById(Long id);

    EventResponseDTO updateEvent(Long eventId, EventUpdateRequestDTO request, Long organizerId);

    void deleteEvent(Long eventId, Long organizerId);
}