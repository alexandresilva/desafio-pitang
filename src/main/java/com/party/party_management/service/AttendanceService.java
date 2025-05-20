package com.party.party_management.service;

import com.party.party_management.dto.UserResponseDTO;

import java.util.List;

public interface AttendanceService {
    void registerAttendance(Long eventId, Long userId);
    void cancelAttendance(Long eventId, Long userId);
    List<UserResponseDTO> getEventAttendees(Long eventId);
}
