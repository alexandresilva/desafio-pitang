package com.party.party_management.service;

import com.party.party_management.dto.EventRequest;
import com.party.party_management.dto.EventResponse;
import com.party.party_management.dto.EventUpdateRequest;
import com.party.party_management.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {
    List<Event> findAllEvents(int page, int size);
    Page<EventResponse> getAllEvents(int page, int size);
    Page<EventResponse> getUpcomingEvents(int page, int size);

    @Transactional
    EventResponse createEvent(EventRequest request);

    EventResponse getEventById(Long id);

    EventResponse updateEvent(Long eventId, EventUpdateRequest request, Long organizerId);

    void deleteEvent(Long eventId, Long organizerId);
}