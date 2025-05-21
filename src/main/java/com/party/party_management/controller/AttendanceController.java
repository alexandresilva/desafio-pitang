package com.party.party_management.controller;

import com.party.party_management.dto.UserResponseDTO;
import com.party.party_management.security.UserDetailsImpl;
import com.party.party_management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/attend")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<Void> attendEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        attendanceService.registerAttendance(eventId, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelAttendance(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        attendanceService.cancelAttendance(eventId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/attendees")
    public ResponseEntity<List<UserResponseDTO>> getAttendees(@PathVariable Long eventId) {
        List<UserResponseDTO> attendees = attendanceService.getEventAttendees(eventId);
        return ResponseEntity.ok(attendees);
    }
}
