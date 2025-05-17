package com.party.party_management.controller;

import com.party.party_management.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/attend")
public class AttendanceController {

    @PostMapping
    public ResponseEntity<Void> attendEvent(@PathVariable Long eventId) {
        // Inscrever-se
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelAttendance(@PathVariable Long eventId) {
        // Cancelar
    }

    @GetMapping("/attendees")
    public ResponseEntity<List<UserResponse>> getAttendees(@PathVariable Long eventId) {
        // Listar participantes
    }
}
